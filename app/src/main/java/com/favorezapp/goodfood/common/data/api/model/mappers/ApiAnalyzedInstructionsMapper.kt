package com.favorezapp.goodfood.common.data.api.model.mappers

import com.favorezapp.goodfood.common.data.api.model.ApiAnalyzedInstructions
import com.favorezapp.goodfood.common.domain.model.foodrecipe.AnalyzedInstructions
import javax.inject.Inject

class ApiAnalyzedInstructionsMapper @Inject constructor(
    private val analyzedInstructionMapper: ApiAnalyzedInstructionMapper
): ApiMapper<ApiAnalyzedInstructions, AnalyzedInstructions> {
    override fun mapToDomain(entity: ApiAnalyzedInstructions): AnalyzedInstructions {
        val instructions = entity.analyzedInstructions.orEmpty().map {
            analyzedInstructionMapper.mapToDomain(it)
        }
        return AnalyzedInstructions( instructions )
    }
}