package com.favorezapp.goodfood.common.presentation.model.mappers

import com.favorezapp.goodfood.common.domain.model.foodrecipe.ExtendedIngredient
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe
import com.favorezapp.goodfood.common.presentation.model.UiExtendedIngredient
import javax.inject.Inject

class UIFoodRecipeMapper @Inject constructor(
    private val uiIngredientMapper: UIIngredientMapper
):
    UIMapper<FoodRecipe, UIFoodRecipe> {

    override fun mapToView(input: FoodRecipe): UIFoodRecipe =
        UIFoodRecipe(
            input.image,
            input.title,
            input.summary,
            input.aggregateLikes,
            input.readyInMinutes,
            input.vegan,
            input.sourceUrl,
            mapExtendedIngredients(input.extendedIngredients),
            input.vegetarian,
            input.glutenFree,
            input.dairyFree,
            input.veryHealthy,
            input.cheap
        )

    private fun mapExtendedIngredients(extendedIngredients: List<ExtendedIngredient>) =
        extendedIngredients.map {
            uiIngredientMapper.mapToView( it )
        }

}