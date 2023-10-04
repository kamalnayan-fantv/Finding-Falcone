package com.kn.domain.usecase

import com.kn.domain.repository.FalconRepository
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 04/10/23
 **/
class GetPlanetsUseCase @Inject constructor(
    private val falconRepository: FalconRepository
) {
    suspend fun invoke() = falconRepository.getPlanets()
}