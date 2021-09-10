package com.favorezapp.goodfood.common.domain.model.foodrecipe

data class FoodRecipe(
    val id: Long?,
    val aggregateLikes: Int,
    val author: String,
    val cheap: Boolean,
    val cuisines: List<String>,
    val dairyFree: Boolean,
    val extendedIngredients: List<ExtendedIngredient>,
    val glutenFree: Boolean,
    val image: String,
    val lowFodmap: Boolean,
    val readyInMinutes: Int,
    val sourceName: String,
    val sourceUrl: String,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean
)