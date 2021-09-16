package com.favorezapp.goodfood.common.domain.model.foodrecipe

data class Step(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val length: Length,
    val number: Int,
    val step: String
)