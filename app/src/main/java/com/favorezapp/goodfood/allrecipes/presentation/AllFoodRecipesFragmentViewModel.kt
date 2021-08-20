package com.favorezapp.goodfood.allrecipes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.favorezapp.goodfood.common.domain.model.NetworkException
import com.favorezapp.goodfood.common.domain.model.NetworkUnavailableException
import com.favorezapp.goodfood.common.domain.model.NoMoreRecipesException
import com.favorezapp.goodfood.common.presentation.Event
import com.favorezapp.goodfood.common.utils.DispatchersProvider
import com.favorezapp.goodfood.common.utils.createExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllFoodRecipesFragmentViewModel @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val dispatchersProvider: DispatchersProvider
): ViewModel() {
    private val _state = MutableLiveData<AllFoodRecipesFragmentViewState>()
    val state: LiveData<AllFoodRecipesFragmentViewState>
        get() = _state

    private var numberOfRecipes = 0

    init {
        _state.value = AllFoodRecipesFragmentViewState()
    }

    fun onEvent(event: AllFoodRecipesEvent) =
        when( event ) {
            is AllFoodRecipesEvent.RequestInitialRecipes -> { loadRecipes() }
            is AllFoodRecipesEvent.RequestMoreRecipes -> {}
        }

    private fun loadRecipes() {
        if( state.value!!.recipes.isEmpty() )
            loadNextRecipes()
    }

    private fun loadNextRecipes() {
        val exceptionHandler = createExceptionHandler("Error loading recipes")

        viewModelScope.launch(exceptionHandler) {
            // Callt the use case
        }
    }

    private fun createExceptionHandler(message: String): CoroutineExceptionHandler =
        viewModelScope.createExceptionHandler(message) { onFailure(it) }

    private fun onFailure(failure: Throwable) {
        when( failure ) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _state.value = _state.value!!.copy(
                    loading = false,
                    failure = Event(failure)
                )
            }
            is NoMoreRecipesException -> {
                _state.value = _state.value!!.copy(
                    failure = Event(failure),
                    noMoreRecipes = true
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}