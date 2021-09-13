package com.favorezapp.goodfood.common.details.presentation

import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe

data class DetailsActivityState(
    val foodRecipe: UIFoodRecipe ?= null,
    val isFavorite: Boolean = false
)