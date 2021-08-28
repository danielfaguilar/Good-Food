package com.favorezapp.goodfood.common.data.di

import com.favorezapp.goodfood.common.data.api.ApiConstants
import com.favorezapp.goodfood.common.data.api.SpooncularApi
import com.favorezapp.goodfood.common.data.api.interceptors.AuthenticationInterceptor
import com.favorezapp.goodfood.common.data.api.interceptors.LoggingInterceptor
import com.favorezapp.goodfood.common.data.api.interceptors.NetworkStatusInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    fun providesHttpLoggingInterceptor(
        logger: LoggingInterceptor
    ): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(logger)

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }

    @Provides
    fun provideOkHttpClient(
        authenticationInterceptor: AuthenticationInterceptor,
        logging: HttpLoggingInterceptor,
        networkStatus: NetworkStatusInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(networkStatus)
        .addInterceptor(authenticationInterceptor)
        .addInterceptor(logging)
        .build()

    @Provides
    fun providesConverterFactory(): Converter.Factory =
        MoshiConverterFactory.create()

    @Provides
    fun providesRetrofit(
        client: OkHttpClient,
        converter: Converter.Factory
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(client)
            .addConverterFactory(converter)
    }

    @Provides
    @Singleton
    fun providesApi(
        retrofit: Retrofit.Builder
    ): SpooncularApi = retrofit
        .build()
        .create(SpooncularApi::class.java)
}