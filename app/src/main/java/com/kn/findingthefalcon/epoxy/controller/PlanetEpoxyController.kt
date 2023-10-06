package com.kn.findingthefalcon.epoxy.controller

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.kn.findingthefalcon.epoxy.models.PlanetWithVehicleModel
import com.kn.findingthefalcon.epoxy.models.PlanetWithVehicleModel_
import com.kn.findingthefalcon.epoxy.models.planetWithVehicle
import com.kn.model.response.PlanetsEntity
import com.kn.model.response.VehicleEntity

/** @Author Kamal Nayan
Created on: 05/10/23
 **/
class PlanetEpoxyController : AsyncEpoxyController() {

    var selectionMap: HashMap<String, String>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    var onVehicleClicked :((VehicleEntity, PlanetsEntity)->Unit)?=null

    var planetsList: List<PlanetsEntity>? = null
        set(value) {
            field= value
            requestModelBuild()
        }

    var vehicleList: List<VehicleEntity>? = null
        set(value) {
            field= value
            requestModelBuild()
        }
    override fun buildModels() {
        buildPlanetViews()
    }

    private fun buildPlanetViews() {
        planetsList?.forEachIndexed { index, planetItem ->
             planetWithVehicle {
                 id("planet_with_vehicle_$index")
                 vehicleList(this@PlanetEpoxyController.vehicleList)
                 planetItem(planetItem)
                 onVehicleClicked { vehicleEntity, planetsEntity ->
                     this@PlanetEpoxyController.onVehicleClicked?.invoke(vehicleEntity,planetsEntity)
                 }
            }
        }
    }
}