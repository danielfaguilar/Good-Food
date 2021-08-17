package com.favorezapp.goodfood.common.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiFoodRecipe(
    @Json(name = "results")
    val apiResults: List<ApiResult>
)