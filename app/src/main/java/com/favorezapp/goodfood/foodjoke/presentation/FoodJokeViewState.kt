package com.favorezapp.goodfood.foodjoke.presentation

import com.favorezapp.goodfood.common.presentation.util.Event

data class FoodJokeViewState(
    val joke: String = "",
    val failure: Event<Throwable>? = null,
    val loading: Boolean = true
)