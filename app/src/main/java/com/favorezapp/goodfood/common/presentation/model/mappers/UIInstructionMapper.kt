package com.favorezapp.goodfood.common.presentation.model.mappers

import com.favorezapp.goodfood.common.domain.model.foodrecipe.AnalyzedInstruction
import com.favorezapp.goodfood.common.presentation.model.UiInstruction
import javax.inject.Inject

class UIInstructionMapper @Inject constructor(
    private val uiStepMapper: UIStepMapper
): UIMapper<AnalyzedInstruction, UiInstruction> {
    override fun mapToView(input: AnalyzedInstruction) =
        UiInstruction(
            input.name,
            input.steps.map {
                uiStepMapper.mapToView(it)
            }
        )
}