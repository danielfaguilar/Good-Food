package com.favorezapp.goodfood.common.data.cache

import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFoodRecipeAggregate
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFavoriteFoodRecipe
import com.favorezapp.goodfood.foodjoke.data.cache.model.CachedFoodJoke
import io.reactivex.Flowable

interface Cache {
    fun getFoodRecipes(): Flowable<List<CachedFoodRecipeAggregate>>
    suspend fun getLocalFoodRecipes(): List<CachedFoodRecipeAggregate>
    suspend fun storeFoodRecipes(foodRecipes: List<CachedFoodRecipeAggregate>)
    suspend fun deleteAllRecipes()
    suspend fun getFoodRecipeByTitle( title: String ): CachedFoodRecipeAggregate

    suspend fun saveFavoriteRecipe( recipe: CachedFavoriteFoodRecipe)
    fun getFavoriteRecipes(): Flowable<List<CachedFavoriteFoodRecipe>>
    suspend fun isFavoriteRecipe( title: String ): Boolean
    suspend fun deleteAllFavoriteRecipes()
    suspend fun deleteFavoriteRecipeById( title: String )

    suspend fun storeFoodJoke( joke: CachedFoodJoke )
    fun getFoodJoke(): Flowable<CachedFoodJoke>
    suspend fun deleteFoodJoke()
}