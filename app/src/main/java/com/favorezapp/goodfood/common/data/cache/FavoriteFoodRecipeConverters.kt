package com.favorezapp.goodfood.common.data.cache

import androidx.room.TypeConverter
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavoriteFoodRecipeConverters {
    private val gson = Gson()

    @TypeConverter
    fun foodRecipeToString( foodRecipe: FoodRecipe ): String {
        return gson.toJson( foodRecipe )
    }

    @TypeConverter
    fun stringToFoodRecipe( str: String ): FoodRecipe {
        val type = object: TypeToken<FoodRecipe>() {}.type
        return gson.fromJson( str, type )
    }
}