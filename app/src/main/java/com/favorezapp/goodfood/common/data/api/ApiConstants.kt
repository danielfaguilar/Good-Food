package com.favorezapp.goodfood.common.data.api

import com.favorezapp.goodfood.BuildConfig

object ApiConstants {
    const val BASE_ENDPOINT = "https://api.spoonacular.com/"
    const val RECIPES_ENDPOINT = "recipes/complexSearch"
    const val FOOD_JOKE_ENDPOINT = "food/jokes/random"

    const val EXTENDED_INGREDIENT_ENDPOINT = "https://spoonacular.com/cdn/ingredients_100x100/"

    const val API_KEY = BuildConfig.Key
}

object ApiParameters {
    const val KEY_PARAM = "apiKey"
    const val NUMBER_PARAM = "number"
    const val TYPE_PARAM = "type"
    const val DIET_PARAM = "diet"
    const val QUERY_PARAM = "query"

    const val ADD_RECIPE_INFORMATION_QUERY = "addRecipeInformation"
    const val FILL_INGREDIENTS_QUERY = "fillIngredients"
}