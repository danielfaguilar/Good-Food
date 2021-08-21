package com.favorezapp.goodfood.common.data.api

import com.favorezapp.goodfood.BuildConfig

object ApiConstants {
    const val BASE_ENDPOINT = "https://api.spoonacular.com/"
    const val RECIPES_ENDPOINT = "recipes/complexSearch"

    const val API_KEY = BuildConfig.Key
}

object ApiParameters {
    const val KEY_QUERY = "apiKey"
}