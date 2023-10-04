package com.kn.model.response

import com.kn.commons.base.BaseResponse
import kotlinx.parcelize.Parcelize

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
@Parcelize
data class TokenResponse(
 val token: String
) : BaseResponse()