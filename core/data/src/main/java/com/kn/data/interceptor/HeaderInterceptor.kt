package com.kn.data.interceptor

/** @Author Kamal Nayan
Created on: 14/10/23
 **/

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Intercepts all network requests and adds header
 * to it.
 */
@Singleton
class HeaderInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = request.newBuilder().apply {
            addHeader("Accept", "application/json")
        }.build()

        return chain.proceed(request)
    }
}
