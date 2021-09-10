package com.favorezapp.goodfood.common.data.cache.models.foodrecipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.favorezapp.goodfood.common.domain.model.foodrecipe.ExtendedIngredient
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe

@Entity( tableName = "food_recipe_table" )
data class CachedFoodRecipe(
    val foodRecipeId: Long?,
    val aggregateLikes: Int,
    val author: String,
    val cheap: Boolean,
    val dairyFree: Boolean,
    val glutenFree: Boolean,
    val image: String,
    val lowFodmap: Boolean,
    val readyInMinutes: Int,
    val sourceName: String,
    val sourceUrl: String,
    val summary: String,
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean
) {
    companion object {
        fun fromDomain( recipe: FoodRecipe ) = with( recipe ) {
            CachedFoodRecipe(
                id,
                aggregateLikes,
                author,
                cheap,
                dairyFree,
                glutenFree,
                image,
                lowFodmap,
                readyInMinutes,
                sourceName,
                sourceUrl,
                summary,
                title,
                vegan,
                vegetarian,
                veryHealthy
            )
        }
    }

    fun toDomain(
        cuisines: List<CachedCuisine>,
        extendedIngredients: List<CachedExtendedIngredient>
    ) = FoodRecipe(
        foodRecipeId,
        aggregateLikes,
        author,
        cheap,
        cuisines.map { it.cuisine },
        dairyFree,
        mapExtendedIngredients(extendedIngredients),
        glutenFree,
        image,
        lowFodmap,
        readyInMinutes,
        sourceName,
        sourceUrl,
        summary,
        title,
        vegan,
        vegetarian,
        veryHealthy
    )

    private fun mapExtendedIngredients(ei: List<CachedExtendedIngredient>): List<ExtendedIngredient>{
        return ei.map {
            ExtendedIngredient(
                it.ingredientId,
                it.amount,
                it.consistency,
                it.image,
                it.name,
                it.original,
                it.unit
            )
        }
    }
}