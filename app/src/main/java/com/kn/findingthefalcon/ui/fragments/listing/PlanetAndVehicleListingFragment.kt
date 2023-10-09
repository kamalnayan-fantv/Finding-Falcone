package com.kn.findingthefalcon.ui.fragments.listing

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kn.findingthefalcon.databinding.FragmentPlanetAndVehicleListingBinding
import com.kn.findingthefalcon.epoxy.controller.PlanetEpoxyController
import com.kn.findingthefalcon.event.VehicleSelectionEvent
import com.kn.ui.R
import com.kn.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlanetAndVehicleListingFragment : BaseFragment<FragmentPlanetAndVehicleListingBinding>(
    FragmentPlanetAndVehicleListingBinding::inflate) {


    private val viewModel by viewModels<PlanetAndVehicleListingViewModel>()
    private val controller by lazy {
        PlanetEpoxyController()
    }
    companion object {
        fun newInstance() = PlanetAndVehicleListingFragment()
    }

    override fun setViewModelToBinding() {
        
    }

    override fun initViews() {
        with(binding){
                    planetsEpoxy.setController(controller)
            }
    }

    override fun setData() {
        with(viewModel) {
            getPlanets()
            getVehicles()
        }
    }

    override fun setListeners() {
        with(controller){
            onVehicleClicked={vehicle,planet->
                viewModel.onVehicleClick(vehicle,planet)
            }
        }
    }

    override fun setObservers() {
        with(viewModel) {
            planetData.observe(viewLifecycleOwner) { response ->
                response?.let {
                    controller.planetsList = it
                }
            }

            vehiclesData.observe(viewLifecycleOwner) { response ->
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
                Toast.makeText(requireContext(),getString(R.string.format_vehicle_not_available,event.vehicleName),
                    Toast.LENGTH_LONG).show()
            }

            VehicleSelectionEvent.MaximumPlanetsSelected ->   Toast.makeText(requireContext(),R.string.error_max_planets_selected.toStringFromResourceId(),
                Toast.LENGTH_LONG).show()
        }
    }
}