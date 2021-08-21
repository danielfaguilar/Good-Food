package com.favorezapp.goodfood.common.data.api.interceptors

import com.favorezapp.goodfood.common.data.api.ApiConstants
import com.favorezapp.goodfood.common.data.api.ApiParameters
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val httpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(ApiParameters.KEY_QUERY, ApiConstants.API_KEY)
            .build()

        val requestBuilder = originalRequest.newBuilder()
            .url(httpUrl)

        val authRequest = requestBuilder.build()
        return chain.proceed(authRequest)
    }
}