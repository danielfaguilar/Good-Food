package com.favorezapp.goodfood.allrecipes.filterrecipes.presentation

import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealAndDietType

data class BottomSheetFragmentViewState(
    val mealTypeResId: Int = 0,
    val dietTypeResId: Int = 0,
    val mealAndDietType: MealAndDietType = MealAndDietType()
)