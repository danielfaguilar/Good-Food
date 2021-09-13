package com.favorezapp.goodfood.foodjoke.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.foodjoke.domain.model.FoodJoke
import io.reactivex.Flowable
import javax.inject.Inject

class GetFoodJoke @Inject constructor(
    private val repository: RecipesRepository
) {
    operator fun invoke(): Flowable<FoodJoke> {
        return repository.getFoodJoke()
    }
}