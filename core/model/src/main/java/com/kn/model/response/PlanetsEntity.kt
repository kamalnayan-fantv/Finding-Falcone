package com.kn.model.response

import com.kn.commons.base.BaseResponse
import kotlinx.parcelize.Parcelize

/** @Author Kamal Nayan
Created on: 03/10/23
 **/
@Parcelize
data class PlanetsEntity(
 val name:String,
 val distance:Int,
):BaseResponse()