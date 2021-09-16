package com.favorezapp.goodfood.common.domain

import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.domain.model.pagination.PaginatedFoodRecipes
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealAndDietType
import com.favorezapp.goodfood.common.domain.model.network.NetworkResponseState
import com.favorezapp.goodfood.foodjoke.domain.model.FoodJoke
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    suspend fun requestMoreFoodRecipes(numOfItems: Int): NetworkResponseState<PaginatedFoodRecipes>
    suspend fun searchAndGetRecipes(numOfItems: Int, query: String): NetworkResponseState<PaginatedFoodRecipes>
    suspend fun storeFoodRecipes(foodRecipes: List<FoodRecipe>)
    fun getRecipes(): Flowable<List<FoodRecipe>>
    suspend fun getLocalRecipes(): List<FoodRecipe>
    fun getMealAndDietType(): Flow<MealAndDietType>
    suspend fun saveMealAndDietType(types: MealAndDietType)
    suspend fun deleteAllRecipes()
    suspend fun getFoodRecipeByTitle(title: String): FoodRecipe

    suspend fun saveFavoriteRecipe(foodRecipe: FoodRecipe)
    fun getFavoriteRecipes(): Flowable<List<FoodRecipe>>
    suspend fun isFavoriteRecipe(title: String): Boolean
    suspend fun deleteAllFavoriteFoodRecipes()
    suspend fun deleteFavoriteFoodRecipeByTitle(title: String )

    suspend fun requestFoodJoke(): NetworkResponseState<FoodJoke>

    suspend fun deleteFoodJoke()
    fun getFoodJoke(): Flowable<FoodJoke>
    suspend fun storeFoodJoke( joke: FoodJoke )
}