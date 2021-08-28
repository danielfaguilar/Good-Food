package com.favorezapp.goodfood.common.data.cache

import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFoodRecipeAggregate
import io.reactivex.Flowable

interface Cache {
    fun getFoodRecipes(): Flowable<List<CachedFoodRecipeAggregate>>
    suspend fun storeFoodRecipes(foodRecipes: List<CachedFoodRecipeAggregate>)
}