package com.kn.findingthefalcon.event

import androidx.annotation.StringRes

/** @Author Kamal Nayan
Created on: 09/10/23
 **/
sealed class FindingFalconStatusEvent {
    data class Found(val planetName:String):FindingFalconStatusEvent()

    data object NotFound:FindingFalconStatusEvent()

    data class Error(val message:String):FindingFalconStatusEvent()

    /**
     * [message] is a [StringRes] so it can have [formatArgs]
     */
    data class InvalidRequest(@StringRes val message:Int,val formatArgs:List<Any>?=null):FindingFalconStatusEvent()
}