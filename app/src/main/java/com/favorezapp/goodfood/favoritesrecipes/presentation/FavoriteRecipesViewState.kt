package com.favorezapp.goodfood.favoritesrecipes.presentation

import com.favorezapp.goodfood.common.presentation.util.Event
import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe

data class FavoriteRecipesViewState(
    val favRecipes: List<UIFoodRecipe> = emptyList(),
    val failure: Event<Throwable>? = null
)