package com.favorezapp.goodfood.common.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPaginationFoodRecipe(
    @Json(name = "results")
    val foodRecipes: List<ApiFoodRecipe>,
    @Json(name = "totalResults")
    val total: Int
)