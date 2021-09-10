package com.favorezapp.goodfood.common.data.cache.models.foodrecipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe

@Entity( tableName = "favorite_food_recipes" )
data class CachedFavoriteFoodRecipe(
    @PrimaryKey( autoGenerate = false )
    val title: String,
    val foodRecipe: FoodRecipe
) {
    companion object {
        fun fromDomain( recipe: FoodRecipe ) =
            CachedFavoriteFoodRecipe(
                title = recipe.title,
                foodRecipe = recipe
            )
    }

    fun toDomain() = foodRecipe
}