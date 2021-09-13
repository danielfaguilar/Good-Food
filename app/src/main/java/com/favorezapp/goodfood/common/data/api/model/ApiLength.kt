package com.favorezapp.goodfood.common.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiLength(
    @Json(name = "number")
    val number: Int,
    @Json(name = "unit")
    val unit: String
)