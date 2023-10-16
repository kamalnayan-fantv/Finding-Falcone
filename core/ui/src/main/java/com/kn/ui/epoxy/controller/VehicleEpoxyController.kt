package com.kn.ui.epoxy.controller

import com.airbnb.epoxy.AsyncEpoxyController
import com.kn.model.response.VehicleEntity
import com.kn.ui.epoxy.models.vehicle
import com.kn.ui.vehicleShimmer

/** @Author Kamal Nayan
Created on: 05/10/23
 **/
class VehicleEpoxyController : AsyncEpoxyController() {

    var vehicleList: List<VehicleEntity>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    var selectedVehicle: VehicleEntity? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    var onVehicleClick: ((VehicleEntity) -> Unit)? = null

    override fun buildModels() {
        buildVehiclesView()
    }

    /**
     * Will show shimmer till data is arrived
     */
    private fun buildVehiclesView() {
        vehicleList?.forEachIndexed { index, vehicleEntity ->
            vehicle {
                id("vehicle_$index")
                vehicleItem(vehicleEntity)
                showAsSelected(vehicleEntity.name == this@VehicleEpoxyController.selectedVehicle?.name)
                onVehicleClick {
                    this@VehicleEpoxyController.onVehicleClick?.invoke(it)
                }
            }
        } ?: run {
            (1..5).forEach {
                vehicleShimmer {
                    id("shimmer_$it")
                }
            }
        }
    }
}