package com.kn.findingthefalcon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kn.commons.base.BaseViewModel
import com.kn.domain.usecase.GetPlanetsUseCase
import com.kn.domain.usecase.GetVehiclesUseCase
import com.kn.model.response.PlanetsEntity
import com.kn.model.response.VehicleEntity
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
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


    private val _vehiclesData by lazy{ MutableLiveData<List<VehicleEntity>>()}
     val vehiclesData: LiveData<List<VehicleEntity>> by lazy{_vehiclesData}


    fun getPlanets() {
        viewModelScope.launch {
            val response = getPlanetsUseCase.invoke()
           response.onSuccess {
               _planetData.postValue(this.data)
           }
        }
    }

    fun getVehicles(){
        viewModelScope.launch {
            val response = getVehiclesUseCase.invoke()
            response.onSuccess {
                _vehiclesData.postValue(this.data)
            }
        }
    }
}