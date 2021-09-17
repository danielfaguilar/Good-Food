package com.favorezapp.goodfood.common.data.cache.daos

import androidx.room.*
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.*
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFavoriteFoodRecipe
import com.favorezapp.goodfood.common.data.cache.models.instructions.CachedAnalyzedInstruction
import com.favorezapp.goodfood.common.domain.model.foodrecipe.AnalyzedInstructions
import io.reactivex.Flowable

@Dao
abstract class FoodRecipeDao {
    @Transaction
    @Query("SELECT * FROM food_recipe_table")
    abstract fun getAllFoodRecipes(): Flowable<List<CachedFoodRecipeAggregate>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun storeFoodRecipeAggregate(
        foodRecipe: CachedFoodRecipe,
        extendedIngredients: List<CachedExtendedIngredient>,
        cuisines: List<CachedCuisine>,
        analyzedInstructions: List<CachedAnalyzedInstruction>
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun storeCachedRecipeIngredientCrossRef( ref: CachedRecipeIngredientCrossRef )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun storeCachedFoodRecipeCuisineCrossRef( ref: CachedFoodRecipeCuisineCrossRef )

    suspend fun insertFoodRecipes(foodRecipes: List<CachedFoodRecipeAggregate>) {
        for (recipe in foodRecipes) {
            storeFoodRecipeAggregate(
                recipe.foodRecipe,
                recipe.extendedIngredients,
                recipe.cuisines,
                recipe.analyzedInstructions
            )
            insertIngredient( recipe )
            insertCuisines( recipe )
        }
    }

    private suspend fun insertCuisines(recipe: CachedFoodRecipeAggregate) {
        for( cuisine in recipe.cuisines )
            storeCachedFoodRecipeCuisineCrossRef(
                CachedFoodRecipeCuisineCrossRef(
                    recipe.foodRecipe.title,
                    cuisine.cuisine
                )
            )
    }

    private suspend fun insertIngredient(recipe: CachedFoodRecipeAggregate) {
        for( ingredient in recipe.extendedIngredients )
            storeCachedRecipeIngredientCrossRef(
                CachedRecipeIngredientCrossRef(
                    recipe.foodRecipe.title,
                    ingredient.name
                )
            )
    }

    @Transaction
    @Query("SELECT * FROM food_recipe_table")
    abstract suspend fun getFoodRecipesAggregate(): List<CachedFoodRecipeAggregate>

    @Query("DELETE FROM food_recipe_table")
    abstract suspend fun deleteCachedRecipes()

    @Query("DELETE FROM extended_ingredient_table")
    abstract suspend fun deleteCachedExtendedIngredient()

    @Query("DELETE FROM cuisine_table")
    abstract suspend fun deleteCachedCuisines()

    suspend fun deleteAllRecipes() {
        deleteCachedRecipes()
        deleteCachedCuisines()
        deleteAnalyzedInstructions()
        deleteCachedExtendedIngredient()

        deleteRecipeCuisinesCrossRef()
        deleteRecipeExtIngredientsCrossRef()
    }

    @Query("DELETE FROM CachedRecipeIngredientCrossRef")
    abstract fun deleteRecipeExtIngredientsCrossRef()

    @Query("DELETE FROM CachedFoodRecipeCuisineCrossRef")
    abstract fun deleteRecipeCuisinesCrossRef()

    @Query("DELETE FROM analyzed_instruction")
    abstract suspend fun deleteAnalyzedInstructions()

    @Transaction
    @Query("SELECT * FROM food_recipe_table WHERE title IN (:title)")
    abstract suspend fun getCachedRecipeByTitle( title: String ): CachedFoodRecipeAggregate


    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract suspend fun saveFavoriteFoodRecipe( favoriteRecipe: CachedFavoriteFoodRecipe)

    @Query("SELECT * FROM favorite_food_recipes")
    abstract fun getFavoriteFoodRecipes(): Flowable<List<CachedFavoriteFoodRecipe>>

    @Query("SELECT EXISTS( SELECT * FROM favorite_food_recipes WHERE title IN (:title) )")
    abstract suspend fun isFavoriteRecipe( title: String ): Boolean

    @Query("DELETE FROM favorite_food_recipes")
    abstract suspend fun deleteAllFavoriteRecipes()

    @Query("DELETE FROM favorite_food_recipes WHERE title IN (:title)")
    abstract fun deleteFavoriteRecipeByTitle( title: String )
}
