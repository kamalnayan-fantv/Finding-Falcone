package com.kn.ui.epoxy.models

import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.core.text.bold
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
           setSpeed(this)
            setTotalCountText(this)
            if(showAsSelected){
                binding.parentContainer.setBackgroundResource(R.drawable.bg_round_selected)
            }else{
                binding.parentContainer.setBackgroundResource(R.drawable.bg_round_unselected)
            }
        }
    }

    private fun setSpeed(binding: LayoutEpoxyVehicleItemBinding) {
        with(binding) {
            val speedText = SpannableStringBuilder().apply {
                bold {
                    append(binding.root.context.getString(R.string.text_speed))
                }
                append(" : ")
                append(
                    binding.root.context.getString(
                        R.string.format_vehicle_speed,
                        vehicleItem?.speed.toString()
                    )
                )
            }
            tvSpeed.setText(speedText, TextView.BufferType.SPANNABLE)
        }
    }

    private fun setTotalCountText(binding: LayoutEpoxyVehicleItemBinding) {
        with(binding) {
            val spannableString = SpannableStringBuilder().apply {
                bold {
                    append(binding.root.context.getString(R.string.text_total_count))
                }
                append(" : ")
                append(vehicleItem?.totalNumber.toString() ?: 0.toString())
            }
            tvCount.setText(spannableString, TextView.BufferType.SPANNABLE)
        }
    }
}