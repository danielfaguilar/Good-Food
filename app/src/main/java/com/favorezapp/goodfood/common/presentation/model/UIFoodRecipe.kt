package com.favorezapp.goodfood.common.presentation.model

data class UIFoodRecipe(
    val id: Long,
    val image: String,
    val name: String,
    val description: String,
    val likes: Int,
    val readyInMinutes: Int,
    val vegan: Boolean
)