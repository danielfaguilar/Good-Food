package com.favorezapp.goodfood.common.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPaginationFoodRecipes(
    @Json(name = "results")
    val foodRecipes: List<ApiFoodRecipe>?,
    @Json(name = "number")
    val numOfRecipes: Int?,
    @Json(name = "totalResults")
    val totalRecipes: Int?
)