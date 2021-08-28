package com.favorezapp.goodfood.common.data.cache

import com.favorezapp.goodfood.common.data.cache.daos.FoodRecipeDao
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFoodRecipeAggregate
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val foodRecipeDao: FoodRecipeDao
): Cache {
    override fun getFoodRecipes() = foodRecipeDao.getAllFoodRecipes()
    override suspend fun storeFoodRecipes(foodRecipes: List<CachedFoodRecipeAggregate>) {
        foodRecipeDao.insertFoodRecipes(foodRecipes)
    }
}