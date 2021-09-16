package com.favorezapp.goodfood.foodjoke.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.domain.model.network.NetworkResponseState
import com.favorezapp.goodfood.foodjoke.domain.model.FoodJoke
import javax.inject.Inject

class RequestNextFoodJoke @Inject constructor(
    private val repository: RecipesRepository
){
    suspend operator fun invoke(): NetworkResponseState<FoodJoke> {
        val state = repository.requestFoodJoke()

        if( state is NetworkResponseState.Success<FoodJoke> ) {
            val joke: FoodJoke = state.result
            repository.deleteFoodJoke()
            repository.storeFoodJoke(joke)
        }

        return state
    }
}