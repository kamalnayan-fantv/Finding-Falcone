package com.kn.findingthefalcon.epoxy.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kn.ui.base.BaseEpoxyModel
import com.kn.findingthefalcon.R
import com.kn.findingthefalcon.databinding.LayoutEpoxyPlanetItemBinding
import com.kn.model.response.PlanetsEntity
import com.kn.model.response.VehicleEntity

/** @Author Kamal Nayan
Created on: 05/10/23
 **/
@EpoxyModelClass
abstract class PlanetWithVehicleModel : EpoxyModelWithHolder<PlanetWithVehicleModel.Holder>() {

//    BaseEpoxyModel<LayoutEpoxyPlanetItemBinding>(R.layout.layout_epoxy_planet_item) {

    @EpoxyAttribute
    var vehicleList: List<VehicleEntity>? = null

    @EpoxyAttribute
    var planetItem: PlanetsEntity? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        setupUi(holder.binding)
    }

    private fun setupUi(binding: LayoutEpoxyPlanetItemBinding) {
        with(binding) {
            planetItem?.let { planet ->
                planetView.apply {
                    vehicleList?.let {
                        setVehicles(it)
                    }
                    setPlanetItem(planet)
                    setPlanetName(planet.name)
                    setPlanetDistance(planet.distance)
                }
            }
        }
    }

    inner class Holder : EpoxyHolder() {
        lateinit var binding: LayoutEpoxyPlanetItemBinding
        override fun bindView(itemView: View) {
            binding = LayoutEpoxyPlanetItemBinding.bind(itemView)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.layout_epoxy_planet_item
    }

//    override fun LayoutEpoxyPlanetItemBinding.bind() {
//        setupUi(this)
//    }
}