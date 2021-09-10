package com.favorezapp.goodfood.common.data.cache

import com.favorezapp.goodfood.common.data.cache.daos.FoodRecipeDao
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFoodRecipeAggregate
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFavoriteFoodRecipe
import com.favorezapp.goodfood.foodjoke.data.cache.daos.FoodJokeDao
import com.favorezapp.goodfood.foodjoke.data.cache.model.CachedFoodJoke
import io.reactivex.Flowable
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val foodRecipeDao: FoodRecipeDao,
    private val jokeDao: FoodJokeDao
): Cache {
    override fun getFoodRecipes() = foodRecipeDao.getAllFoodRecipes()
    override suspend fun getLocalFoodRecipes(): List<CachedFoodRecipeAggregate> {
        return foodRecipeDao.getFoodRecipesAggregate()
    }

    override suspend fun storeFoodRecipes(foodRecipes: List<CachedFoodRecipeAggregate>) {
        foodRecipeDao.insertFoodRecipes(foodRecipes)
    }
    override suspend fun deleteAllRecipes() {
        foodRecipeDao.deleteAllRecipes()
    }

    override suspend fun getFoodRecipeByTitle(title: String): CachedFoodRecipeAggregate {
        return foodRecipeDao.getCachedRecipeByTitle(title)
    }

    override suspend fun saveFavoriteRecipe(recipe: CachedFavoriteFoodRecipe) {
        return foodRecipeDao.saveFavoriteFoodRecipe( recipe )
    }

    override fun getFavoriteRecipes(): Flowable<List<CachedFavoriteFoodRecipe>> {
        return foodRecipeDao.getFavoriteFoodRecipes()
    }

    override suspend fun isFavoriteRecipe(title: String): Boolean {
        return foodRecipeDao.isFavoriteRecipe( title )
    }

    override suspend fun deleteAllFavoriteRecipes() {
        foodRecipeDao.deleteAllFavoriteRecipes()
    }

    override suspend fun deleteFavoriteRecipeById(title: String) {
        foodRecipeDao.deleteFavoriteRecipeByTitle(title)
    }

    override suspend fun storeFoodJoke(joke: CachedFoodJoke) {
        jokeDao.insertFoodJoke( joke )
    }

    override fun getFoodJoke(): Flowable<CachedFoodJoke> {
        return jokeDao.getFoodJoke()
    }

    override suspend fun deleteFoodJoke() {
        jokeDao.deleteFoodJoke()
    }
}