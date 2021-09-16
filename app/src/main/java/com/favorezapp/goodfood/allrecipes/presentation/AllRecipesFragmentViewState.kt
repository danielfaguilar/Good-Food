package com.favorezapp.goodfood.allrecipes.presentation

import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe
import com.favorezapp.goodfood.common.presentation.util.Event

data class AllRecipesFragmentViewState(
    val loading: Boolean = true,
    val recipes: List<UIFoodRecipe> = emptyList(),
    val noMoreRecipes: Boolean = false,

    val errorCode: Event<Int>? = null,
    val errorMessage: Event<String>? = null,

    val internetConnection: Boolean = false,
    val searchingRemotely: Boolean = false
)