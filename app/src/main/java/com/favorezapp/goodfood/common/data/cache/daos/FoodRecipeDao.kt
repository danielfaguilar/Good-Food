package com.favorezapp.goodfood.common.data.cache.daos

import androidx.room.*
import io.reactivex.Flowable
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedCuisine
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFoodRecipe
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedExtendedIngredient
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFoodRecipeAggregate

@Dao
abstract class FoodRecipeDao {
    @Transaction
    @Query("SELECT * FROM food_recipe_table")
    abstract fun getAllFoodRecipes(): Flowable<List<CachedFoodRecipeAggregate>>

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract suspend fun storeFoodRecipeAggregate(
        foodRecipe: CachedFoodRecipe,
        extendedIngredients: List<CachedExtendedIngredient>,
        cuisines: List<CachedCuisine>
    )

    suspend fun insertFoodRecipes(foodRecipes: List<CachedFoodRecipeAggregate>) {
        for( recipe in foodRecipes )
            storeFoodRecipeAggregate(
                recipe.foodRecipe,
                recipe.extendedIngredients,
                recipe.cuisines
            )
    }
}
