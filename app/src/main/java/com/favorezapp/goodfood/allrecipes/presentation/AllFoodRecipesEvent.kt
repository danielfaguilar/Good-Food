package com.favorezapp.goodfood.allrecipes.presentation

sealed class AllFoodRecipesEvent {
    object RequestInitialRecipes: AllFoodRecipesEvent()
    object RequestMoreRecipes: AllFoodRecipesEvent()
}