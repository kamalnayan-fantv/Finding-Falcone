package com.kn.ui.filter.vehicle

import com.kn.model.response.VehicleEntity

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
interface EligibleVehicleFilter {
    fun getEligibleVehicles(planetDistance: Int, vehicles: List<VehicleEntity>):List<VehicleEntity>
}