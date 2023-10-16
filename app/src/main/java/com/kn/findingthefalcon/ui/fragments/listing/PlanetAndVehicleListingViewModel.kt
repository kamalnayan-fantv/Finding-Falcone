package com.kn.findingthefalcon.ui.fragments.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kn.commons.base.BaseViewModel
import com.kn.commons.utils.annotation.Status
import com.kn.commons.utils.constants.Constants.REQUIRED_SELECTED_PLANETS_COUNT
import com.kn.domain.repository.LocalStorageRepository
import com.kn.domain.usecase.FindFalconUseCase
import com.kn.domain.usecase.GetPlanetsUseCase
import com.kn.domain.usecase.GetTokenUseCase
import com.kn.domain.usecase.GetVehiclesUseCase
import com.kn.findingthefalcon.event.FindingFalconStatusEvent
import com.kn.findingthefalcon.event.VehicleSelectionEvent
import com.kn.model.body.FindFalconBody
import com.kn.model.response.FindFalconResponse
import com.kn.model.response.PlanetsEntity
import com.kn.model.response.VehicleEntity
import com.kn.ui.R
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onSuccess
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
@HiltViewModel
class PlanetAndVehicleListingViewModel @Inject constructor(
    private val getPlanetsUseCase: GetPlanetsUseCase,
    private val getVehiclesUseCase: GetVehiclesUseCase,
    private val getTokenUseCase: Lazy<GetTokenUseCase>,
    private val findFalconUseCase: Lazy<FindFalconUseCase>,
    private val localStorageRepository: Lazy<LocalStorageRepository>
) : BaseViewModel() {

    private val _planetData by lazy { MutableLiveData<List<PlanetsEntity>>() }
    val planetData: LiveData<List<PlanetsEntity>> by lazy { _planetData }

    private val _vehiclesData by lazy { MutableLiveData<List<VehicleEntity>>() }
    val vehiclesData: LiveData<List<VehicleEntity>> by lazy { _vehiclesData }

    private val _selectionEvent by lazy { MutableSharedFlow<VehicleSelectionEvent>() }
    val selectionEvent by lazy { _selectionEvent.asSharedFlow() }

    private val _findFalconEvent by lazy { MutableSharedFlow<FindingFalconStatusEvent>() }
    val findFalconEvent by lazy { _findFalconEvent.asSharedFlow()}

    private val _selectionMap = HashMap<String, String>()

    /**
     * Fetches planets data from remote
     */
    fun getPlanets() {
        viewModelScope.launch {
            val response = getPlanetsUseCase.invoke()
            response.onSuccess {
                _planetData.postValue(this.data)
            }
        }
    }

    /**
     * Fetches vehicles data from remote
     *
     * delay is to show shimmer effect
     */
    fun getVehicles() {
        viewModelScope.launch {
            delay(1500)
            val response = getVehiclesUseCase.invoke()
            response.onSuccess {
                _vehiclesData.postValue(this.data)
            }
        }
    }

    /**
     * When a vehicle item is clicked then we check that clicked vehicle is already
     * selected for that planet or not. If yes the de-select otherwise check if it is
     * available or not using [isVehicleAvailable] then [selectVehicleForPlanet] function
     * handles further.
     */
    fun onVehicleClick(vehicle: VehicleEntity, planet: PlanetsEntity) {
        viewModelScope.launch {
            if (_selectionMap.keys.contains(planet.name)) {
                /*
                * Means the selected vehicle card is again clicked hence
                * we need to de-select it. so removing it from map.
                */
                if (_selectionMap[planet.name] == vehicle.name) {
                    _selectionMap.remove(planet.name)
                    val totalTimeTaken = getTotalTime()
                    _selectionEvent.emit(VehicleSelectionEvent.VehicleSelected(_selectionMap,totalTimeTaken))
                    return@launch
                }
            }
            if (isVehicleAvailable(vehicle)) {
                selectVehicleForPlanet(vehicle, planet)
            } else {
                _selectionEvent.emit(VehicleSelectionEvent.VehicleNotAvailable(vehicle.name))
            }
        }
    }

    /**
     * First check maximum number of planets are selected or not using [ifMaxPlanetSelected]
     * then check if the [planet] is not in [_selectionMap]
     * if selected then show error otherwise assign [vehicle] to the [planet] by updating
     * [_selectionMap].
     */
    private fun selectVehicleForPlanet(vehicle: VehicleEntity, planet: PlanetsEntity) {
        /*
         * we will first check if Max Planet is already selected and the planet is not
         * in selection map then return
         * otherwise select that vehicle for this planet
         */
        if (ifMaxPlanetSelected() && isPlanetNotInSelectionMap(planet)) {
            viewModelScope.launch { _selectionEvent.emit(VehicleSelectionEvent.MaximumPlanetsSelected) }
            return
        }
        _selectionMap[planet.name] = vehicle.name

        viewModelScope.launch {
            val totalTimeTaken = getTotalTime()

            _selectionEvent.emit(
                VehicleSelectionEvent.VehicleSelected(
                    _selectionMap,
                    totalTimeTaken
                )
            )
        }
    }

    private suspend fun getTotalTime(): Int {
       return withContext(Dispatchers.Default) {
            var totalTime = 0

            _selectionMap.forEach { entry ->
                _planetData.value?.forEach { planet ->
                    if (planet.name == entry.key) {
                        /* means a vehicle is selected for this planet
                        * so we will find that vehicle
                        */
                        _vehiclesData.value?.forEach { vehicle ->
                            if (vehicle.name == entry.value) {
                                /**
                                 * Vehicle for this planet has been found so now we
                                 * will calculate time.
                                 */
                                /**
                                 * Vehicle for this planet has been found so now we
                                 * will calculate time.
                                 */
                                totalTime += planet.distance / vehicle.speed
                            }
                        }
                    }
                }
            }
           return@withContext totalTime
        }
    }

    private fun isPlanetNotInSelectionMap(planet: PlanetsEntity): Boolean {
        return !_selectionMap.keys.contains(planet.name)
    }

    /**
     * Checks if maximum number of planets have been selected or not.
     * If yes then emit [VehicleSelectionEvent.MaximumPlanetsSelected] and return true
     * else just return false
     */
    private fun ifMaxPlanetSelected(): Boolean {
        return _selectionMap.keys.count() >= REQUIRED_SELECTED_PLANETS_COUNT
    }


    /**
     * Checks the vehicle total number is greater than the count of
     * vehicle in selected state. Otherwise all vehicles are in use.
     * hence, not available.
     */
    private fun isVehicleAvailable(vehicle: VehicleEntity): Boolean {
        val inUseVehiclesCount = _selectionMap.values?.count { it == vehicle.name } ?: return false
        return inUseVehiclesCount < vehicle.totalNumber
    }

    fun findFalcon() {
        viewModelScope.launch {
            val planets = _selectionMap.keys.toList()
            val vehicles = _selectionMap.values.toList()
            val storedToken = localStorageRepository.get().getToken()

            if (planets.size < REQUIRED_SELECTED_PLANETS_COUNT) {
                viewModelScope.launch {
                    _findFalconEvent.emit(
                        FindingFalconStatusEvent.InvalidRequest(
                            R.string.error_required_no_of_planets_not_selected,
                            listOf(REQUIRED_SELECTED_PLANETS_COUNT)
                        )
                    )
                }
                return@launch
            }

            if (storedToken.isNullOrBlank()) {
                val tokenResponse = async { getTokenUseCase.get().invoke() }.await()
                tokenResponse.onSuccess {
                    localStorageRepository.get().setToken(this.data.token)
                }
                val requestBody =
                    FindFalconBody(localStorageRepository.get().getToken(), planets, vehicles)
                val findResponse = findFalconUseCase.get().invoke(requestBody)
               handleFindResponse(findResponse)
            } else {
                val requestBody =
                    FindFalconBody(localStorageRepository.get().getToken(), planets, vehicles)
                val findResponse = findFalconUseCase.get().invoke(requestBody)
                handleFindResponse(findResponse)
            }
        }
    }

    private fun handleFindResponse(findResponse: ApiResponse<FindFalconResponse>) {
        findResponse.onSuccess {
            val data =this.data

            when (data.status) {
                Status.SUCCESS -> {
                    viewModelScope.launch{ _findFalconEvent.emit(FindingFalconStatusEvent.Found(data.planetName)) }
                }
                Status.FAILURE -> {
                    viewModelScope.launch{ _findFalconEvent.emit(FindingFalconStatusEvent.NotFound) }
                }
                else -> {
                    viewModelScope.launch{ _findFalconEvent.emit(FindingFalconStatusEvent.Error(data.error)) }
                }
            }
        }
    }
}
