package com.favorezapp.goodfood.common.data.api.model.mappers

interface ApiMapper<E, D> {
    fun mapToDomain(entity: E): D
}