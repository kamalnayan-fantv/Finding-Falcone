package com.kn.model.response

import com.google.gson.annotations.SerializedName
import com.kn.commons.base.BaseResponse
import kotlinx.parcelize.Parcelize

/** @Author Kamal Nayan
Created on: 03/10/23
 **/
@Parcelize
data class VehicleEntity(
    val name: String,
    @SerializedName("total_no")
    val totalNumber: Int,
    @SerializedName("max_distance")
    val maxDistance: Int,
    val speed: Int,
) : BaseResponse()