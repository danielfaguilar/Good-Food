package com.favorezapp.goodfood.foodjoke.presentation

sealed class FoodJokeEvent {
    object RequestFoodJoke: FoodJokeEvent()
}