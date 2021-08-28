package com.favorezapp.goodfood.common.data.cache.models.foodrecipe

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe

class CachedFoodRecipeAggregate(
    @Embedded
    val foodRecipe: CachedFoodRecipe,
    @Relation(
        parentColumn = "foodRecipeId",
        entityColumn = "ingredientId",
        associateBy = Junction(CachedRecipeIngredientCrossRef::class)
    )
    val extendedIngredients: List<CachedExtendedIngredient>,
    @Relation(
        parentColumn = "foodRecipeId",
        entityColumn = "cuisine",
        associateBy = Junction(CachedFoodRecipeCuisineCrossRef::class)
    )
    val cuisines: List<CachedCuisine>
) {
    companion object {
        fun fromDomain(foodRecipe: FoodRecipe) =
            CachedFoodRecipeAggregate(
                foodRecipe = CachedFoodRecipe.fromDomain(foodRecipe),
                extendedIngredients = foodRecipe.extendedIngredients.map {
                    CachedExtendedIngredient.fromDomain(it)
                },
                cuisines = foodRecipe.cuisines.map {
                    CachedCuisine(it)
                }
            )

    }
}