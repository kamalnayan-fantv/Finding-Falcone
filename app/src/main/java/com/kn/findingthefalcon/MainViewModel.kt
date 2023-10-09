package com.kn.findingthefalcon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kn.commons.base.BaseViewModel
import com.kn.domain.usecase.GetPlanetsUseCase
import com.kn.domain.usecase.GetVehiclesUseCase
import com.kn.findingthefalcon.event.VehicleSelectionEvent
import com.kn.model.response.PlanetsEntity
import com.kn.model.response.VehicleEntity
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPlanetsUseCase: GetPlanetsUseCase,
    private val getVehiclesUseCase: GetVehiclesUseCase,
) : BaseViewModel() {

    private val _planetData by lazy { MutableLiveData<List<PlanetsEntity>>() }
    val planetData: LiveData<List<PlanetsEntity>> by lazy { _planetData }


    private val _vehiclesData by lazy { MutableLiveData<List<VehicleEntity>>() }
    val vehiclesData: LiveData<List<VehicleEntity>> by lazy { _vehiclesData }

    private val _selectionEvent by lazy {  MutableSharedFlow<VehicleSelectionEvent>()}
    val selectionEvent:SharedFlow<VehicleSelectionEvent> by lazy { _selectionEvent }

    private val _selectionMap= HashMap<String,String>()

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
     */
    fun getVehicles() {
        viewModelScope.launch {
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
                if(_selectionMap[planet.name] == vehicle.name){
                    _selectionMap.remove(planet.name)
                    _selectionEvent.emit(VehicleSelectionEvent.VehicleSelected(_selectionMap))
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
     * if selected then show error otherwise assign [vehicle] to the [planet] by updating
     * [_selectionMap].
     */
    private fun selectVehicleForPlanet(vehicle: VehicleEntity, planet: PlanetsEntity) {
        /*
         * Means vehicle for this planet was not selected earlier
         * so we will first check if Max Planet Selected then return
         * otherwise select that vehicle for this planet
         */
        if (ifMaxPlanetSelected()) {
            viewModelScope.launch { _selectionEvent.emit(VehicleSelectionEvent.MaximumPlanetsSelected) }
            return
        }
        _selectionMap[planet.name] = vehicle.name


        viewModelScope.launch {
            _selectionEvent.emit(
                VehicleSelectionEvent.VehicleSelected(
                    _selectionMap
                )
            )
        }
    }

    /**
     * Checks if maximum number of planets have been selected or not.
     * If yes then emit [VehicleSelectionEvent.MaximumPlanetsSelected] and return true
     * else just return false
     */
    private fun ifMaxPlanetSelected(): Boolean {
        return _selectionMap.keys.count() >= 4
    }


    /**
     * Checks the vehicle total number is greater than the count of
     * vehicle in selected state. Otherwise all vehicles are in use.
     * hence, not available.
     */
    private fun isVehicleAvailable(vehicle: VehicleEntity): Boolean {
        val inUseVehiclesCount = _selectionMap.values?.count { it == vehicle.name }?:return false
        return inUseVehiclesCount < vehicle.totalNumber
    }

    fun getToken(){
        viewModelScope.launch {

        }
    }
}