package com.favorezapp.goodfood.favoritesrecipes.presentation

sealed class FavoriteRecipesEvent {
    object DeleteFavoriteRecipes: FavoriteRecipesEvent()
    class DeleteFavoriteRecipeById( val recipeTitle: String ): FavoriteRecipesEvent()
}