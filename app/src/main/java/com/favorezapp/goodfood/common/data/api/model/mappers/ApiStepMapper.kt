package com.favorezapp.goodfood.common.data.api.model.mappers

import com.favorezapp.goodfood.common.data.api.model.ApiStep
import com.favorezapp.goodfood.common.domain.model.foodrecipe.Step
import javax.inject.Inject

class ApiStepMapper @Inject constructor(
    private val equipmentMapper: ApiEquipmentMapper,
    private val apiIngredientMapper: ApiIngredientMapper,
    private val apiLengthMapper: ApiLengthMapper
): ApiMapper<ApiStep, Step> {
    override fun mapToDomain(entity: ApiStep): Step {
        return Step(
            entity.equipment.orEmpty().map {
                equipmentMapper.mapToDomain(it)
            },
            entity.ingredients.orEmpty().map {
                apiIngredientMapper.mapToDomain(it)
            },
            apiLengthMapper.mapToDomain( entity.length ),
            entity.number ?: 0,
            entity.step.orEmpty()
        )
    }
}