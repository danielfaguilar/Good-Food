package com.favorezapp.goodfood.common.data.api.model.mappers

import com.favorezapp.goodfood.common.data.api.model.ApiAnalyzedInstruction
import com.favorezapp.goodfood.common.domain.model.foodrecipe.AnalyzedInstruction
import javax.inject.Inject

class ApiAnalyzedInstructionMapper @Inject constructor(
    private val apiStepMapper: ApiStepMapper
): ApiMapper<ApiAnalyzedInstruction, AnalyzedInstruction> {
    override fun mapToDomain(entity: ApiAnalyzedInstruction): AnalyzedInstruction {
        return AnalyzedInstruction(
            name = entity.name.orEmpty(),
            steps = entity.steps.orEmpty().map {
                apiStepMapper.mapToDomain(it)
            }
        )
    }
}