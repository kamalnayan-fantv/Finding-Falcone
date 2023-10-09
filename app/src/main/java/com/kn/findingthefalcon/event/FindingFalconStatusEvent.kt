package com.kn.findingthefalcon.event

/** @Author Kamal Nayan
Created on: 09/10/23
 **/
sealed class FindingFalconStatusEvent {
    data class Found(val planetName:String):FindingFalconStatusEvent()

    data object NotFound:FindingFalconStatusEvent()

    data class Error(val message:String):FindingFalconStatusEvent()
}