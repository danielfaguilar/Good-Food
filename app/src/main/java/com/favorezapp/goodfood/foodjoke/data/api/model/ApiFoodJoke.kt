package com.favorezapp.goodfood.foodjoke.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiFoodJoke(
    @Json(name = "text")
    val text: String
)