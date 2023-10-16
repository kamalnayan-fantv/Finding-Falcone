package com.kn.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
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
        setupBackground()
        setListeners()
    }

    private fun setupBackground() {
        val backgroundRes = if(selectedVehicle==null)R.drawable.bg_round_stroke else R.drawable.bg_round_selected_dark
        binding.parentContainer.apply {
            setBackgroundResource(backgroundRes)
        }
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

    /**
     * Used to set filter which decides which vehicles are eleigible for
     * this planet.
     * Currently we are using [DefaultVehicleFilter] by default.
     */
    fun setEligibilityFilter(filter: EligibleVehicleFilter) {
        this.filter = filter
        buildVehiclesView()
    }

    fun setSelectedVehicle(vehicle:VehicleEntity?){
        selectedVehicle=vehicle
        buildVehiclesView()
        setupBackground()
        setupTimeTaken(vehicle)
    }

    private fun setupTimeTaken(vehicle: VehicleEntity?) {
        if(vehicle==null){
            binding.tvTimeToReach.isVisible =false
            return
        }
        val timeTakenToReachPlanet = getTimeTaken()
        binding.tvTimeToReach.apply {
            isVisible = true
            text= context.getString(R.string.format_hours_to_reach_planet,timeTakenToReachPlanet.toString(),planetItem?.name.orEmpty())
        }
    }

    /**
     * Returns time taken by selected vehicle to reach this planet.
     */
    fun getTimeTaken():Int {
        val vehicleSpeed = selectedVehicle?.speed ?: return 0
        val time =((planetItem?.distance ?: 0).div(vehicleSpeed))
        return time
    }

    fun setVehicleClickListener( onVehicleClick:((VehicleEntity)->Unit)){
        this.onVehicleClick = onVehicleClick
    }

    private fun buildVehiclesView() {
        with(binding) {
            vehicleEpoxyController.apply {
                vehicleList = filter.getEligibleVehicles(
                    planetDistance = planetItem?.distance ?: 0,
                    vehicles
                )

                selectedVehicle = this@PlanetWithVehicleView.selectedVehicle
            }
        }
    }

}