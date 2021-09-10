package com.favorezapp.goodfood.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.favorezapp.goodfood.common.data.cache.daos.FoodRecipeDao
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.*
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFavoriteFoodRecipe
import com.favorezapp.goodfood.foodjoke.data.cache.daos.FoodJokeDao
import com.favorezapp.goodfood.foodjoke.data.cache.model.CachedFoodJoke

const val DB_VERSION = 2
const val DB_NAME = "spooncular_db"

@Database(
    entities = [
        /* common */
        CachedCuisine::class,
        CachedFoodRecipe::class,
        CachedExtendedIngredient::class,
        CachedRecipeIngredientCrossRef::class,
        CachedFoodRecipeCuisineCrossRef::class,
        /* favorite recipes feature */
        CachedFavoriteFoodRecipe::class,
        /* food joke recipes feature */
        CachedFoodJoke::class
    ],
    version = DB_VERSION
)
@TypeConverters( FavoriteFoodRecipeConverters::class )
abstract class SpooncularDb: RoomDatabase() {
    abstract fun foodRecipeDao(): FoodRecipeDao
    abstract fun foodJokeDao(): FoodJokeDao
}