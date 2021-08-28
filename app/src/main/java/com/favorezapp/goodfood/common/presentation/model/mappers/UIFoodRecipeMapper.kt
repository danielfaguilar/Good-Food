package com.favorezapp.goodfood.common.presentation.model.mappers

import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe
import javax.inject.Inject

class UIFoodRecipeMapper @Inject constructor():
    UIMapper<FoodRecipe, UIFoodRecipe> {

    override fun mapToView(input: FoodRecipe): UIFoodRecipe =
        UIFoodRecipe(
            input.id,
            input.image,
            input.title,
            input.summary,
            input.aggregateLikes,
            input.readyInMinutes,
            input.vegan
        )

}