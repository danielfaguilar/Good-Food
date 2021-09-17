package com.favorezapp.goodfood.common.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

/**
 * No changes happen in the schema
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {}
}

/**
 * Creation of new table for instructions of the food recipe
 */
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `analyzed_instruction` " +
                "(`name` TEXT NOT NULL, `steps` TEXT NOT NULL, " +
                "PRIMARY KEY(`name`))")
    }
}

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
            )
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .build()
        }

        @Provides
        fun providesFoodRecipesDao(
            database: SpooncularDb
        ) = database.foodRecipeDao()

    }
}