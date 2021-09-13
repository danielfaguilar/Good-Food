package com.favorezapp.goodfood.allrecipes.filterrecipes.presentation

sealed class BottomSheetFragmentEvent {
    object SaveMealAndDietType : BottomSheetFragmentEvent()
    object PrepareForChoose : BottomSheetFragmentEvent()

    class MealTypeSelected(val resId: Int, val mealType: String): BottomSheetFragmentEvent()
    class DietTypeSelected(val resId: Int, val dietType: String): BottomSheetFragmentEvent()
}