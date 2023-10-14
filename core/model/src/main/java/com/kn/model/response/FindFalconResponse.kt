package com.kn.model.response

import com.google.gson.annotations.SerializedName
import com.kn.commons.base.BaseResponse
import com.kn.commons.utils.annotation.Status
import kotlinx.parcelize.Parcelize

/** @Author Kamal Nayan
Created on: 09/10/23
 **/
@Parcelize
data class FindFalconResponse(
    @SerializedName("planet_name")
    val planetName:String,

    @SerializedName("status")
    @Status
    val status: String,

    val error:String,

):BaseResponse()
