package com.favorezapp.goodfood.common.data.api.model.mappers

import com.favorezapp.goodfood.common.data.api.model.ApiEquipment
import com.favorezapp.goodfood.common.domain.model.foodrecipe.Equipment
import javax.inject.Inject

class ApiEquipmentMapper @Inject constructor(): ApiMapper<ApiEquipment, Equipment> {
    override fun mapToDomain(entity: ApiEquipment): Equipment {
        return Equipment(
            entity.id,
            entity.image.orEmpty(),
            entity.localizedName.orEmpty(),
            entity.name.orEmpty()
        )
    }
}