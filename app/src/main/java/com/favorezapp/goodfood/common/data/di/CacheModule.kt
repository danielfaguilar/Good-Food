package com.favorezapp.goodfood.common.data.di

import android.content.Context
import androidx.room.Room
import com.favorezapp.goodfood.common.data.cache.Cache
import com.favorezapp.goodfood.common.data.cache.DB_NAME
import com.favorezapp.goodfood.common.data.cache.RoomCache
import com.favorezapp.goodfood.common.data.cache.SpooncularDb
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {
    @Binds
    abstract fun bindsCache( cache: RoomCache ): Cache

    companion object {
        @Singleton
        @Provides
        fun providesSpooncularDatabase(
            @ApplicationContext context: Context
        ): SpooncularDb {
            return Room.databaseBuilder(
                context,
                SpooncularDb::class.java,
                DB_NAME
            ).build()
        }

        @Provides
        fun providesFoodRecipesDao(
            database: SpooncularDb
        ) = database.foodRecipeDao()

    }
}