package com.favorezapp.goodfood.common.domain

import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.domain.model.pagination.PaginatedFoodRecipes
import io.reactivex.Flowable

interface RecipesRepository {
    suspend fun requestMoreFoodRecipes(): PaginatedFoodRecipes
    suspend fun storeFoodRecipes(foodRecipes: List<FoodRecipe>)
    fun getRecipes(): Flowable<List<FoodRecipe>>
}