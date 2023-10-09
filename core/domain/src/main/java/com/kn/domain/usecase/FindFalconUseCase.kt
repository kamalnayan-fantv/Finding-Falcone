package com.kn.domain.usecase

import com.kn.domain.repository.FalconRepository
import com.kn.model.body.FindFalconBody
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 09/10/23
 **/
class FindFalconUseCase @Inject constructor(private val falconRepository: FalconRepository) {
    suspend fun invoke(falconBody: FindFalconBody) = falconRepository.findFalcon(falconBody)
}