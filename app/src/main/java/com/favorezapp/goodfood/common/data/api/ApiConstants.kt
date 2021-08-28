package com.favorezapp.goodfood.common.data.api

import com.favorezapp.goodfood.BuildConfig

object ApiConstants {
    const val BASE_ENDPOINT = "https://api.spoonacular.com/"
    const val RECIPES_ENDPOINT = "recipes/complexSearch"

    const val API_KEY = BuildConfig.Key
}

object ApiParameters {
    const val KEY_QUERY = "apiKey"
    const val NUMBER_QUERY = "number"
    const val TYPE_QUERY = "type"
    const val DIET_QUERY = "diet"
    const val ADD_RECIPE_INFORMATION_QUERY = "addRecipeInformation"
    const val FILL_INGREDIENTS_QUERY = "fillIngredients"
}