package com.favorezapp.goodfood.common.presentation.model

data class UIFoodRecipe(
    val image: String,
    val title: String,
    val summary: String,
    val likes: Int,
    val readyInMinutes: Int,
    val vegan: Boolean,
    val sourceUrl: String,
    val extendedIngredients: List<UiExtendedIngredient>,

    val analyzedInstructions: List<UiInstruction>,

    val vegetarian: Boolean,
    val glutenFree: Boolean,
    val dairyFree: Boolean,
    val healthy: Boolean,
    val cheap: Boolean
)