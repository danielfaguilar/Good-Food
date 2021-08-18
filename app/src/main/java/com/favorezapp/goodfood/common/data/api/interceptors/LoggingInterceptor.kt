package com.favorezapp.goodfood.common.data.api.interceptors

import com.favorezapp.logging.Logger
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class LoggingInterceptor @Inject constructor(): HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Logger.i(message)
    }
}