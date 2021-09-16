
package com.favorezapp.goodfood.common.data.cache

import androidx.room.TypeConverter
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.domain.model.foodrecipe.Step
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FoodRecipeConverters {
    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString( recipe: FoodRecipe ): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToFoodRecipe( recipe: String ): FoodRecipe {
        val type = object: TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(recipe, type)
    }

    @TypeConverter
    fun stepListToString(instructions: List<Step> ): String {
        return gson.toJson(instructions)
    }

    @TypeConverter
    fun stringToStepList( instructions: String ): List<Step> {
        val type = object: TypeToken<List<Step>>(){}.type
        return gson.fromJson( instructions, type )
    }
}