package com.favorezapp.goodfood.allrecipes.presentation

import com.favorezapp.goodfood.common.presentation.util.Event
import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe

data class AllRecipesFragmentViewState(
    val loading: Boolean = true,
    val recipes: List<UIFoodRecipe> = emptyList(),
    val noMoreRecipes: Boolean = false,
    val failure: Event<Throwable>? = null,
    val internetConnection: Boolean = false,
    val searchingRemotely: Boolean = false
) {
    fun updateToLoading(): AllRecipesFragmentViewState {
        return copy(
            loading = true
        )
    }
}