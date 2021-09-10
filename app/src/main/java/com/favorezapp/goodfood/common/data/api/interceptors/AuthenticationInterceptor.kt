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

        /* Modify harcoded parameters */

        val httpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(ApiParameters.KEY_PARAM, ApiConstants.API_KEY)
            .addQueryParameter(ApiParameters.ADD_RECIPE_INFORMATION_QUERY, "true")
            .addQueryParameter(ApiParameters.FILL_INGREDIENTS_QUERY, "true")
            .build()

        /* Modify harcoded parameters */

        val requestBuilder = originalRequest.newBuilder()
            .url(httpUrl)

        val authRequest = requestBuilder.build()
        return chain.proceed(authRequest)
    }
}
