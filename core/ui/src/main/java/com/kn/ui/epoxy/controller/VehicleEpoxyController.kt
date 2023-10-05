package com.kn.ui.epoxy.controller

import com.airbnb.epoxy.AsyncEpoxyController
import com.kn.model.response.VehicleEntity
import com.kn.ui.epoxy.models.VehicleModel
import com.kn.ui.epoxy.models.vehicle

/** @Author Kamal Nayan
Created on: 05/10/23
 **/
class VehicleEpoxyController : AsyncEpoxyController() {

    var vehicleList: List<VehicleEntity>? = null
        set(value) {
            field= value
            requestModelBuild()
        }

    override fun buildModels() {
        buildVehiclesView()
    }

    private fun buildVehiclesView() {
        vehicleList?.forEachIndexed { index, vehicleEntity ->
            vehicle {
                id("vehicle_$index")
                vehicleItem(vehicleEntity)
            }
        }
    }
}