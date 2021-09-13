package com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model

class MealAndDietType(
    val mealType: MealType = MealType.MAIN_COURSE,
    val mealTypeId: Int = 0,
    val dietType: DietType = DietType.GLUTEN_FREE,
    val dietTypeId: Int = 0,
)