package com.favorezapp.goodfood.common.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiStep(
    @Json(name = "equipment")
    val equipment: List<ApiEquipment>,
    @Json(name = "ingredients")
    val ingredients: List<ApiIngredient>,
    @Json(name = "length")
    val length: ApiLength,
    @Json(name = "number")
    val number: Int,
    @Json(name = "step")
    val step: String
)