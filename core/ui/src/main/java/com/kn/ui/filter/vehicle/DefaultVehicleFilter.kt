package com.kn.ui.filter.vehicle

import com.kn.model.response.VehicleEntity

/** @Author Kamal Nayan
Created on: 04/10/23
 *
 * Returns eligible vehicles for a planet.
 * This default filter just checks that `planetDistance`
 * is equal or less than vehicle max distance.
 **/
class DefaultVehicleFilter : EligibleVehicleFilter {
    override fun getEligibleVehicles(
        planetDistance: Int,
        vehicles: List<VehicleEntity>?
    ): List<VehicleEntity>? {
        return vehicles?.filter { it.maxDistance >= planetDistance }?:null
    }

}
