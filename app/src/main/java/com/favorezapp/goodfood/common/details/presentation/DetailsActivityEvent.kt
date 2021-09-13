package com.favorezapp.goodfood.common.details.presentation

sealed class DetailsActivityEvent {
    class GetFoodRecipe( val title: String ): DetailsActivityEvent()
    object SaveFavoriteRecipe: DetailsActivityEvent()
    object RemoveFavoriteRecipe: DetailsActivityEvent()
}