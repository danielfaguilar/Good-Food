package com.favorezapp.goodfood.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.favorezapp.goodfood.common.data.cache.daos.FoodRecipeDao
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.*

const val DB_VERSION = 1
const val DB_NAME = "spooncular_db"

@Database(
    entities = [
        CachedCuisine::class,
        CachedFoodRecipe::class,
        CachedExtendedIngredient::class,
        CachedRecipeIngredientCrossRef::class,
        CachedFoodRecipeCuisineCrossRef::class
    ],
    version = DB_VERSION
)
abstract class SpooncularDb: RoomDatabase() {
    abstract fun foodRecipeDao(): FoodRecipeDao
}