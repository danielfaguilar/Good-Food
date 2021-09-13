package com.favorezapp.goodfood.allrecipes.presentation

sealed class AllRecipesEvent {
    object RequestInitialRecipes: AllRecipesEvent()
    object RequestNewRecipes: AllRecipesEvent()
    object RequestLocalRecipes : AllRecipesEvent()

    class OnSearchInputSubmit( val query: String ): AllRecipesEvent()
    class OnNetworkStatusChanged( val netStatus: Boolean ): AllRecipesEvent()
}