package com.favorezapp.goodfood.common.presentation.model

data class UIFoodRecipe(
    val id: Long,
    val image: String,
    val title: String,
    val summary: String,
    val likes: Int,
    val readyInMinutes: Int,
    val vegan: Boolean
)