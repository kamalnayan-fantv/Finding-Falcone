package com.kn.ui.epoxy.models

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.kn.commons.utils.extensions.setSafeClickListener
import com.kn.ui.base.BaseEpoxyModel
import com.kn.model.response.VehicleEntity
import com.kn.ui.R
import com.kn.ui.databinding.LayoutEpoxyVehicleItemBinding

/** @Author Kamal Nayan
Created on: 05/10/23
 **/
@EpoxyModelClass
abstract class VehicleModel : BaseEpoxyModel<LayoutEpoxyVehicleItemBinding>(R.layout.layout_epoxy_vehicle_item) {

    @EpoxyAttribute
    var vehicleItem:VehicleEntity?=null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onVehicleClick:((VehicleEntity)->Unit)?=null

    @EpoxyAttribute
    var showAsSelected:Boolean=false

    override fun LayoutEpoxyVehicleItemBinding.bind() {
        setupUi(this)
        setListeners(this)
    }

    private fun setListeners(binding: LayoutEpoxyVehicleItemBinding) {
        with(binding){
            root.setSafeClickListener{
                vehicleItem?.let { it1 -> onVehicleClick?.invoke(it1) }
            }
        }
    }

    private fun setupUi(binding: LayoutEpoxyVehicleItemBinding) {
        with(binding){
            tvVehicleName.text = vehicleItem?.name
            tvSpeed.text = binding.root.context.getString(R.string.format_vehicle_speed,vehicleItem?.speed.toString())
            if(showAsSelected){
                binding.parentContainer.setBackgroundResource(R.drawable.bg_round_selected)
            }else{
                binding.parentContainer.setBackgroundResource(R.drawable.bg_round_unselected)
            }
        }
    }
}