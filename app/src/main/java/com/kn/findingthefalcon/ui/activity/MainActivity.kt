package com.kn.findingthefalcon.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.kn.findingthefalcon.ui.fragments.listing.PlanetAndVehicleListingViewModel
import com.kn.ui.base.BaseActivity
import com.kn.findingthefalcon.databinding.ActivityMainBinding
import com.kn.findingthefalcon.epoxy.controller.PlanetEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}