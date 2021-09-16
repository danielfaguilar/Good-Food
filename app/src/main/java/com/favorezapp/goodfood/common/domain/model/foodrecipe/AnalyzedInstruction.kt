package com.favorezapp.goodfood.common.domain.model.foodrecipe

data class AnalyzedInstruction(
    val recipeTitle: String? = null,
    val name: String,
    val steps: List<Step>
)