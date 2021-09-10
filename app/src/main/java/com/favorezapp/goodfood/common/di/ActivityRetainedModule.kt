package com.favorezapp.goodfood.common.di

import com.favorezapp.goodfood.common.data.SpooncularRecipesRepository
import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.utils.CoroutineDispatchersProvider
import com.favorezapp.goodfood.common.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    @ActivityRetainedScoped
    abstract  fun bindsRepository( repo: SpooncularRecipesRepository ): RecipesRepository

    companion object {
        @Provides
        fun providesCompositeDisposable() = CompositeDisposable()
    }
    @Binds
    abstract fun bindsDispatchersProvider( provider: CoroutineDispatchersProvider ): DispatchersProvider
}
