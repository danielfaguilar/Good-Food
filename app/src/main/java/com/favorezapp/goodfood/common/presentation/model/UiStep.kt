package com.favorezapp.goodfood.common.presentation.model

data class UiStep(
    val equipment: List<UiEquipment>?,
    val ingredients: List<UiIngredient>?,
    val length: UiLength?,
    val number: Int?,
    val step: String?
)
