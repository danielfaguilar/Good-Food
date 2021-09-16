package com.favorezapp.goodfood.common.presentation.model.mappers

import com.favorezapp.goodfood.common.domain.model.foodrecipe.Step
import com.favorezapp.goodfood.common.presentation.model.UiStep
import javax.inject.Inject

class UIStepMapper @Inject constructor(
    private val uiEquipmentMapper: UIEquipmentMapper,
    private val uiIngredientMapper: UIIngredientMapper,
    private val uiLengthMapper: UILengthMapper

): UIMapper<Step, UiStep> {
    override fun mapToView(input: Step): UiStep{
        return UiStep(
            input.equipment.map { uiEquipmentMapper.mapToView(it) },
            input.ingredients.map { uiIngredientMapper.mapToView(it) },
            uiLengthMapper.mapToView(input.length),
            input.number,
            input.step
        )
    }
}