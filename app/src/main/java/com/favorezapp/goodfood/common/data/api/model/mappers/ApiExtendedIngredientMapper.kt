package com.favorezapp.goodfood.common.data.api.model.mappers

import com.favorezapp.goodfood.common.data.api.model.ApiExtendedIngredient
import com.favorezapp.goodfood.common.domain.model.foodrecipe.ExtendedIngredient
import javax.inject.Inject

class ApiExtendedIngredientMapper @Inject constructor():
    ApiMapper<ApiExtendedIngredient, ExtendedIngredient>{

    override fun mapToDomain(entity: ApiExtendedIngredient) =
        ExtendedIngredient(
            entity.id ?: throw MappingException("ID cannot be null"),
            entity.amount ?: 0.0,
            entity.consistency.orEmpty(),
            entity.image.orEmpty(),
            entity.name.orEmpty(),
            entity.original.orEmpty(),
            entity.unit.orEmpty()
        )

}