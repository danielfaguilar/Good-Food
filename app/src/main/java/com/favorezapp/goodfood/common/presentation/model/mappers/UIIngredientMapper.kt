package com.favorezapp.goodfood.common.presentation.model.mappers

import com.favorezapp.goodfood.common.domain.model.foodrecipe.Ingredient
import com.favorezapp.goodfood.common.presentation.model.UiIngredient
import javax.inject.Inject

class UIIngredientMapper @Inject constructor(): UIMapper<Ingredient, UiIngredient> {
    override fun mapToView(input: Ingredient) =
        UiIngredient(input.image, input.name)
}