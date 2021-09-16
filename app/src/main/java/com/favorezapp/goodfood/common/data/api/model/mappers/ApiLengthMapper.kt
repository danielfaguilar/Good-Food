package com.favorezapp.goodfood.common.data.api.model.mappers

import com.favorezapp.goodfood.common.data.api.model.ApiLength
import com.favorezapp.goodfood.common.domain.model.foodrecipe.Length
import javax.inject.Inject

class ApiLengthMapper @Inject constructor(): ApiMapper<ApiLength?, Length> {
    override fun mapToDomain(entity: ApiLength?): Length {
        return Length(
            entity?.number ?: 0,
            entity?.unit.orEmpty()
        )
    }
}