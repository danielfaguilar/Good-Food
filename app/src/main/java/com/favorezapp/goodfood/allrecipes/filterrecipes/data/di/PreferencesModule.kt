package com.favorezapp.goodfood.allrecipes.filterrecipes.data.di

import com.favorezapp.goodfood.allrecipes.filterrecipes.data.preferences.DataStorePreferences
import com.favorezapp.goodfood.allrecipes.filterrecipes.data.preferences.DataStorePreferencesTypes
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn( SingletonComponent::class )
interface PreferencesModule {
    @Binds
    @Singleton
    fun bindsPreferences( prefs: DataStorePreferencesTypes ): DataStorePreferences
}
