package com.favorezapp.goodfood.common.presentation.model.mappers

interface UIMapper<E, V> {
    fun mapToView(input: E): V
}