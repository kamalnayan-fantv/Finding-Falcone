package com.kn.domain.usecase

import com.kn.domain.repository.FalconRepository
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 09/10/23
 **/
class GetTokenUseCase @Inject constructor(private val falconRepository: FalconRepository) {
 suspend operator fun invoke()=falconRepository.getToken()
}