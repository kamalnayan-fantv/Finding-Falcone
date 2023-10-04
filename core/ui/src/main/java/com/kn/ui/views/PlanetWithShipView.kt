package com.kn.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kn.model.response.VehicleEntity
import com.kn.ui.R
import com.kn.ui.databinding.LayoutPlanetWithShipViewBinding
import com.kn.ui.filter.vehicle.DefaultVehicleFilter
import com.kn.ui.filter.vehicle.EligibleVehicleFilter

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
class PlanetWithShipView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ConstraintLayout(context, attr, defStyleAttr) {

    private val binding: LayoutPlanetWithShipViewBinding
    private var vehicles: List<VehicleEntity>? = null
    private var filter: EligibleVehicleFilter = DefaultVehicleFilter()

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_planet_with_ship_view,
            this,
            true
        )
        // LayoutPlanetWithShipViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setPlanetName(name: String) {
        binding.tvPlanetName.text = name
    }

    fun setPlanetDistance(distance: Int) {
        binding.tvDistance.text = distance.toString()
    }

    fun setVehicles(vehicleList: List<VehicleEntity>) {
        this.vehicles = vehicleList
    }

    fun setEligibilityFilter(filter: EligibleVehicleFilter) {
        this.filter = filter
    }
}