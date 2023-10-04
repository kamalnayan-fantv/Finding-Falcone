package com.kn.commons.base

import com.skydoves.sandwich.ApiResponse

/** @Author Kamal Nayan
Created on: 04/10/23
 **/

/**
 * Base class of Remote API Data source
 */
abstract class BaseRemoteDataSource {

 /**
  * Method to parse the Response of API Service
  * @param T the type of Response
  * @param request api request to be executed
  * @return Output<T> the result of the request with type T
  */
 suspend fun <T> getResponse(
  request: suspend () -> ApiResponse<T>,
 ): ApiResponse<T> {
  return request.invoke()
 }

}