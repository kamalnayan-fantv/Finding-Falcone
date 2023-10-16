package com.kn.findingthefalcon.ui.fragments.listing

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.kn.commons.utils.annotation.Status
import com.kn.commons.utils.extensions.setSafeClickListener
import com.kn.commons.utils.extensions.showToast
import com.kn.findingthefalcon.databinding.FragmentPlanetAndVehicleListingBinding
import com.kn.findingthefalcon.epoxy.controller.PlanetEpoxyController
import com.kn.findingthefalcon.event.FindingFalconStatusEvent
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
        with(binding) {
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

        with(binding){
            btnFind.setSafeClickListener {
                viewModel.findFalcon()
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
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    launch {
                        findFalconEvent.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                            .collectLatest { event ->
                                handleFindFalconResultEvent(event)
                            }
                    }

                    launch {
                        selectionEvent.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                            .collectLatest { event ->
                                handleSelectionEvent(event)
                            }
                    }
                }
            }
        }
    }

    private fun handleFindFalconResultEvent(event: FindingFalconStatusEvent) {
        when (event) {
            is FindingFalconStatusEvent.Error -> {
                requireContext().showToast(event.toString(), Toast.LENGTH_LONG)
            }

            is FindingFalconStatusEvent.Found -> {
                findNavController().navigate(
                    PlanetAndVehicleListingFragmentDirections.actionListingFragmentToResultFragment(
                        Status.SUCCESS,
                        event.planetName
                    )
                )
            }

            FindingFalconStatusEvent.NotFound -> {
                findNavController().navigate(
                    PlanetAndVehicleListingFragmentDirections.actionListingFragmentToResultFragment(
                        Status.FAILURE,
                        null
                    )
                )
            }

            is FindingFalconStatusEvent.InvalidRequest -> {
               handleInvalidRequestEvent(event)
            }
        }
    }

    /**
     * If event has arguments for string then will show with args otherwise
     * will show the resource string
     */
    private fun handleInvalidRequestEvent(event: FindingFalconStatusEvent.InvalidRequest) {
        if (event.formatArgs.isNullOrEmpty())
            requireContext().showToast(event.message.toStringFromResourceId())
        else {
            val formatArgs = event.formatArgs.toTypedArray()
            requireContext().showToast(event.message.toStringFromResourceId(*formatArgs))
        }
    }


    private fun handleSelectionEvent(event: VehicleSelectionEvent) {
        when(event){
            is VehicleSelectionEvent.VehicleSelected ->{
             onVehicleSelected(event)
            }

            is VehicleSelectionEvent.VehicleNotAvailable ->{
                Toast.makeText(requireContext(),R.string.format_vehicle_not_available.toStringFromResourceId(event.vehicleName),
                    Toast.LENGTH_LONG).show()
            }

            VehicleSelectionEvent.MaximumPlanetsSelected ->   Toast.makeText(requireContext(),R.string.error_max_planets_selected.toStringFromResourceId(),
                Toast.LENGTH_LONG).show()
        }
    }

    /**
     * After a vehicle is selected then update the ui
     * accordingly
     */
    private fun onVehicleSelected(event: VehicleSelectionEvent.VehicleSelected) {
        controller.selectionMap=event.data
        binding.tvTotalTime.apply {
            text = R.string.format_total_time_taken.toStringFromResourceId(event.totalTimeTaken.toString())
            isVisible = event.totalTimeTaken> 0
        }
    }
}