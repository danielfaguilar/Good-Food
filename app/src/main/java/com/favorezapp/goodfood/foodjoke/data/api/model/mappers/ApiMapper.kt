package com.favorezapp.goodfood.foodjoke.data.api.model.mappers

interface ApiMapper<E, out D> {
    fun mapTo(api: E): D
}