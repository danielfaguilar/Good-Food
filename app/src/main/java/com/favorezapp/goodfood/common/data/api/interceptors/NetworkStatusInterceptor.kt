package com.favorezapp.goodfood.common.data.api.interceptors

import com.favorezapp.goodfood.common.data.api.util.ConnectionManager
import com.favorezapp.goodfood.common.domain.model.NetworkUnavailableException
import okhttp3.Interceptor
import javax.inject.Inject

class NetworkStatusInterceptor @Inject constructor(
    private val connectionManager: ConnectionManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain) =
        if( connectionManager.isConnected )
            chain.proceed(chain.request())
        else
            throw NetworkUnavailableException()
}