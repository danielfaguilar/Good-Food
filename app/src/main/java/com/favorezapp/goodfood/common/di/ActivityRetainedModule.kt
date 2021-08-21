package com.favorezapp.goodfood.common.di

import com.favorezapp.goodfood.common.utils.CoroutineDispatchersProvider
import com.favorezapp.goodfood.common.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ActivityRetainedModule::class)
abstract class ActivityRetainedModule {
    companion object {
        @Provides
        fun providesCompositeDisposable() = CompositeDisposable()
    }
    @Binds
    abstract fun bindsDispatchersProvider( provider: CoroutineDispatchersProvider ): DispatchersProvider
}