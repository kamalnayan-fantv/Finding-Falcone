package com.kn.findingthefalcon

import android.os.Bundle
import androidx.activity.viewModels
import com.kn.ui.base.BaseActivity
import com.kn.findingthefalcon.databinding.ActivityMainBinding
import com.kn.findingthefalcon.epoxy.controller.PlanetEpoxyController
import dagger.hilt.android.AndroidEntryPoint

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
        }
    }

    private fun getData() {
        with(viewModel) {
            getPlanets()
            getVehicles()
        }
    }
}