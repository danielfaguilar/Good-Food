package com.favorezapp.goodfood.common.domain.model.foodrecipe


data class ExtendedIngredient(
    val id: Long?,
    val amount: Double,
    val consistency: String,
    val image: String,
    val name: String,
    val original: String,
    val unit: String
)