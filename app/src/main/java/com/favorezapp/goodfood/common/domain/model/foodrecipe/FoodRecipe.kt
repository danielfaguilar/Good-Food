package com.favorezapp.goodfood.common.domain.model.foodrecipe

data class FoodRecipe(
    val id: Int,
    val aggregateLikes: Int,
    val author: String,
    val cheap: Boolean,
    val cuisines: List<String>,
    val dairyFree: Boolean,
    val apiExtendedIngredients: List<ExtendedIngredient>,
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