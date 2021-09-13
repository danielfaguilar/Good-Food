package com.favorezapp.goodfood.foodjoke.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.favorezapp.goodfood.common.presentation.util.Event
import com.favorezapp.goodfood.common.utils.DispatchersProvider
import com.favorezapp.goodfood.common.utils.createExceptionHandler
import com.favorezapp.goodfood.foodjoke.domain.model.FoodJoke
import com.favorezapp.goodfood.foodjoke.domain.usecases.GetFoodJoke
import com.favorezapp.goodfood.foodjoke.domain.usecases.RequestNextFoodJoke
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FoodJokeViewModel @Inject constructor(
    private val requestNextFoodJoke: RequestNextFoodJoke,
    private val getFoodJoke: GetFoodJoke,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {
    private var _state = MutableLiveData<FoodJokeViewState>()
    val state: LiveData<FoodJokeViewState> get() = _state
    var foodJokeStr = ""

    init {
        _state.value = FoodJokeViewState()

        observerFoodJokeUpdates()
    }

    private fun observerFoodJokeUpdates() {
        getFoodJoke()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onFoodJokeObtained(it) },
                {}
            ).addTo(compositeDisposable)
    }

    fun onEvent( event: FoodJokeEvent ) {
        when( event ) {
            is FoodJokeEvent.RequestFoodJoke -> { requestFoodJoke() }
        }
    }

    private fun requestFoodJoke() {
        val exceptionHandler = viewModelScope
            .createExceptionHandler("Error getting food joke") {
                onFailure( it )
            }

        viewModelScope.launch( exceptionHandler ) {
            withContext(dispatchersProvider.io()) {
                requestNextFoodJoke()
            }
        }
    }

    private fun onFailure(fail: Throwable) {
        _state.value = state.value!!.copy(
            failure = Event(fail),
            loading = false
        )
    }

    private fun onFoodJokeObtained(foodJoke: FoodJoke) {
        foodJokeStr = foodJoke.joke

        _state.value = state.value!!.copy(
            joke = foodJoke.joke,
            loading = false
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}