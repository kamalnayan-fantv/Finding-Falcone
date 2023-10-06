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
import kotlinx.coroutines.flow.MutableStateFlow
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

    fun getPlanets() {
        viewModelScope.launch {
            val response = getPlanetsUseCase.invoke()
            response.onSuccess {
                _planetData.postValue(this.data)
            }
        }
    }

    fun getVehicles() {
        viewModelScope.launch {
            val response = getVehiclesUseCase.invoke()
            response.onSuccess {
                _vehiclesData.postValue(this.data)
            }
        }
    }

    fun onVehicleClick(vehicle: VehicleEntity, planet: PlanetsEntity) {
        viewModelScope.launch {
            if (isVehicleAvailable(vehicle)) {
                selectVehicleForPlanet(vehicle, planet)
            } else {
                _selectionEvent.emit(VehicleSelectionEvent.VehicleNotAvailable(vehicle.name))
            }
        }
    }

    private fun selectVehicleForPlanet(vehicle: VehicleEntity, planet: PlanetsEntity) {
        _selectionMap[planet.name] = vehicle.name
        viewModelScope.launch {
            _selectionEvent.emit(
                VehicleSelectionEvent.VehicleSelected(
                    _selectionMap
                )
            )
        }
    }

    private fun isVehicleAvailable(vehicle: VehicleEntity): Boolean {
        val inUseVehiclesCount = _selectionMap.values?.count { it == vehicle.name }?:return false
        return inUseVehiclesCount < vehicle.totalNumber
    }
}