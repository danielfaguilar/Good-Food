package com.favorezapp.goodfood.common.presentation.model.mappers

import com.favorezapp.goodfood.common.domain.model.foodrecipe.Length
import com.favorezapp.goodfood.common.presentation.model.UiLength
import javax.inject.Inject

class UILengthMapper @Inject constructor(): UIMapper<Length, UiLength> {
    override fun mapToView(input: Length) =
        UiLength(input.number, input.unit)
}