package com.kn.domain.repository

import com.kn.model.body.FindFalconBody
import com.kn.model.response.FindFalconResponse
import com.kn.model.response.PlanetsEntity
import com.kn.model.response.TokenResponse
import com.kn.model.response.VehicleEntity
import com.skydoves.sandwich.ApiResponse

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
interface FalconRepository {
 suspend fun getPlanets(): ApiResponse<List<PlanetsEntity>>

 suspend fun getVehicles():ApiResponse<List<VehicleEntity>>

 suspend fun getToken(): ApiResponse<TokenResponse>

 suspend fun findFalcon(falconBody: FindFalconBody): ApiResponse<FindFalconResponse>
}