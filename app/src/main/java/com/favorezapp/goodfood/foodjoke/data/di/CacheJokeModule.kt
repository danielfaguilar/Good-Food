package com.favorezapp.goodfood.foodjoke.data.di

import com.favorezapp.goodfood.common.data.cache.SpooncularDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheJokeModule {
    @Provides
    @Singleton
    fun bindsFoodJokeDao(
        db: SpooncularDb
    ) = db.foodJokeDao()
}