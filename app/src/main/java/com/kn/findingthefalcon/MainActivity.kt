package com.kn.findingthefalcon

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.kn.ui.base.BaseActivity
import com.kn.findingthefalcon.databinding.ActivityMainBinding
import com.kn.findingthefalcon.epoxy.controller.PlanetEpoxyController
import com.kn.findingthefalcon.event.VehicleSelectionEvent
import com.kn.ui.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel by viewModels<MainViewModel>()
    private val controller by lazy {
        PlanetEpoxyController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        getData()
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        with(controller){
            onVehicleClicked={vehicle,planet->
                viewModel.onVehicleClick(vehicle,planet)
            }
        }
    }

    private fun initUi() {
        binding?.let { mainBinding ->
            with(mainBinding) {
                planetsEpoxy.setController(controller)
            }
        }
    }

    private fun setObservers() {
        with(viewModel) {
            planetData.observe(this@MainActivity) { response ->
                response?.let {
                    controller.planetsList = it
                }
            }

            vehiclesData.observe(this@MainActivity) { response ->
                response?.let {
                    controller.vehicleList = it
                }
            }

            lifecycleScope.launch {
                selectionEvent.collectLatest {event->
                  handleSelectionEvent(event)
                }
            }
        }
    }

    private fun handleSelectionEvent(event: VehicleSelectionEvent) {
        when(event){
            is VehicleSelectionEvent.VehicleSelected ->{
                controller.selectionMap=event.data
            }

            is VehicleSelectionEvent.VehicleNotAvailable ->{
                Toast.makeText(this,getString(R.string.format_vehicle_not_available,event.vehicleName),Toast.LENGTH_LONG).show()
            }

            VehicleSelectionEvent.MaximumPlanetsSelected ->   Toast.makeText(this,getString(R.string.error_max_planets_selected),Toast.LENGTH_LONG).show()
        }
    }

    private fun getData() {
        with(viewModel) {
            getPlanets()
            getVehicles()
        }
    }
}