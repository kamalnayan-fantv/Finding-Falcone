package com.kn.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kn.model.response.PlanetsEntity
import com.kn.model.response.VehicleEntity
import com.kn.ui.R
import com.kn.ui.databinding.LayoutPlanetWithVehicleViewBinding
import com.kn.ui.epoxy.controller.VehicleEpoxyController
import com.kn.ui.filter.vehicle.DefaultVehicleFilter
import com.kn.ui.filter.vehicle.EligibleVehicleFilter

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
class PlanetWithVehicleView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ConstraintLayout(context, attr, defStyleAttr) {

    private val binding: LayoutPlanetWithVehicleViewBinding
    private var vehicles: List<VehicleEntity>? = null
    private var filter: EligibleVehicleFilter = DefaultVehicleFilter()
    private val vehicleEpoxyController = VehicleEpoxyController()
    private var planetItem: PlanetsEntity? = null
    private var selectedVehicle:VehicleEntity?=null
    private var onVehicleClick:((VehicleEntity)->Unit)?=null


    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_planet_with_vehicle_view,
            this,
            true
        )
        binding.shipsEpoxy.setController(vehicleEpoxyController)
        setListeners()
        // LayoutPlanetWithShipViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun setListeners() {
        with(binding){

        }

        with(vehicleEpoxyController){
            this.onVehicleClick={
                this@PlanetWithVehicleView.onVehicleClick?.invoke(it)
            }
        }
    }

    fun setPlanetName(name: String) {
        binding.tvPlanetName.text = name
    }

    fun setPlanetDistance(distance: Int) {
        binding.tvDistance.text = distance.toString()
    }

    fun setVehicles(vehicleList: List<VehicleEntity>) {
        this.vehicles = vehicleList
        buildVehiclesView()
    }

    fun setPlanetItem(planetsEntity: PlanetsEntity){
        planetItem = planetsEntity
        buildVehiclesView()
    }

    fun setEligibilityFilter(filter: EligibleVehicleFilter) {
        this.filter = filter
        buildVehiclesView()
    }

    fun setSelectedVehicle(vehicle:VehicleEntity?){
        selectedVehicle=vehicle
        buildVehiclesView()
    }

    fun setVehicleClickListener( onVehicleClick:((VehicleEntity)->Unit)){
        this.onVehicleClick = onVehicleClick
    }

    private fun buildVehiclesView() {
        with(binding) {
            vehicleEpoxyController.apply {
                vehicleList = filter.getEligibleVehicles(
                    planetDistance = planetItem?.distance ?: 0,
                    vehicles ?: emptyList()
                )

                selectedVehicle = this@PlanetWithVehicleView.selectedVehicle
            }
        }
    }

}