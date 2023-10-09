package com.kn.model.body

import com.google.gson.annotations.SerializedName

/** @Author Kamal Nayan
Created on: 09/10/23
 **/
data class FindFalconBody(
    val token:String,
    @SerializedName("planet_names")
    val planets:List<String>,
    @SerializedName("vehicle_names")
    val vehicles:List<String>,
)
