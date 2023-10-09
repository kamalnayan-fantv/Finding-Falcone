package com.kn.data.remote

import com.kn.commons.base.BaseRemoteDataSource
import com.kn.data.api.FalconService
import com.kn.model.body.FindFalconBody
import com.kn.model.response.FindFalconResponse
import com.kn.model.response.PlanetsEntity
import com.kn.model.response.TokenResponse
import com.kn.model.response.VehicleEntity
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
class FalconRemoteDataSource @Inject constructor(private val falconService: FalconService) :
    BaseRemoteDataSource() {

    suspend fun getPlanets(): ApiResponse<List<PlanetsEntity>> {
        return getResponse (request = {
            falconService.getPlanets()
        })
    }

    suspend fun getVehicles(): ApiResponse<List<VehicleEntity>> {
        return getResponse(request =  {
            falconService.getVehicles()
        })
    }

    suspend fun getToken(): ApiResponse<TokenResponse> {
        return falconService.getToken()
    }

    suspend fun findFalcon(falconBody: FindFalconBody):ApiResponse<FindFalconResponse>{
        return falconService.findFalcon(falconBody)
    }
}