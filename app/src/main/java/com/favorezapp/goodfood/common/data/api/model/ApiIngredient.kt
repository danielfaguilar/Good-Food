package com.favorezapp.goodfood.common.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiIngredient(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "localizedName")
    val localizedName: String?,
    @Json(name = "name")
    val name: String?
)