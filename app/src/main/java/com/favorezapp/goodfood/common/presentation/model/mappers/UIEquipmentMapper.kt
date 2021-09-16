package com.favorezapp.goodfood.common.presentation.model.mappers

import com.favorezapp.goodfood.common.domain.model.foodrecipe.Equipment
import com.favorezapp.goodfood.common.presentation.model.UiEquipment
import javax.inject.Inject

class UIEquipmentMapper @Inject constructor(): UIMapper<Equipment, UiEquipment> {
    override fun mapToView(input: Equipment) =
        UiEquipment(input.image, input.name)
}