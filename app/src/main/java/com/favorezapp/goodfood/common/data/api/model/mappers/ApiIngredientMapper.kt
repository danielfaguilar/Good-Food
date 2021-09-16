package com.favorezapp.goodfood.common.data.api.model.mappers

import com.favorezapp.goodfood.common.data.api.model.ApiIngredient
import com.favorezapp.goodfood.common.domain.model.foodrecipe.Ingredient
import javax.inject.Inject

class ApiIngredientMapper @Inject constructor(): ApiMapper<ApiIngredient, Ingredient> {
    override fun mapToDomain(entity: ApiIngredient): Ingredient {
        return Ingredient(
            entity.id,
            entity.image.orEmpty(),
            entity.localizedName.orEmpty(),
            entity.name.orEmpty()
        )
    }
}