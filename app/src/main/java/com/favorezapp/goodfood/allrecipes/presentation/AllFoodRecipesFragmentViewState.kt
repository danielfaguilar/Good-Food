package com.favorezapp.goodfood.allrecipes.presentation

import com.favorezapp.goodfood.common.presentation.Event
import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe

data class AllFoodRecipesFragmentViewState(
    val loading: Boolean = false,
    val recipes: List<UIFoodRecipe> = emptyList(),
    val noMoreRecipes: Boolean = false,
    val failure: Event<Throwable>? = null
)