package com.kn.data.repository

import com.kn.data.remote.FalconRemoteDataSource
import com.kn.domain.repository.FalconRepository
import com.kn.model.body.FindFalconBody
import com.kn.model.response.FindFalconResponse
import com.kn.model.response.PlanetsEntity
import com.kn.model.response.TokenResponse
import com.kn.model.response.VehicleEntity
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
class FalconRepositoryImpl @Inject constructor(
    private val falconRemoteDataSource: FalconRemoteDataSource
) : FalconRepository {
    override suspend fun getPlanets(): ApiResponse<List<PlanetsEntity>> {
        return withContext(Dispatchers.IO) { falconRemoteDataSource.getPlanets() }
    }

    override suspend fun getVehicles(): ApiResponse<List<VehicleEntity>> {
        return falconRemoteDataSource.getVehicles()
    }

    override suspend fun getToken(): ApiResponse<TokenResponse> {
        return falconRemoteDataSource.getToken()
    }

    override suspend fun findFalcon(falconBody: FindFalconBody): ApiResponse<FindFalconResponse> {
        return falconRemoteDataSource.findFalcon(falconBody)
    }

}