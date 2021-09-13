package com.favorezapp.goodfood.foodjoke.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import javax.inject.Inject

class RequestNextFoodJoke @Inject constructor(
    private val repository: RecipesRepository
){
    suspend operator fun invoke() {
        val joke = repository.requestFoodJoke()
        repository.deleteFoodJoke()
        repository.storeFoodJoke( joke )
    }
}