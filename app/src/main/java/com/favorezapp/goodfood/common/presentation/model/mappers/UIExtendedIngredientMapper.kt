package com.favorezapp.goodfood.common.presentation.model.mappers

import com.favorezapp.goodfood.common.domain.model.foodrecipe.ExtendedIngredient
import com.favorezapp.goodfood.common.presentation.model.UiExtendedIngredient
import javax.inject.Inject

class UIExtendedIngredientMapper @Inject constructor(): UIMapper<ExtendedIngredient, UiExtendedIngredient> {
    override fun mapToView(input: ExtendedIngredient): UiExtendedIngredient {
        return with( input ) {
            UiExtendedIngredient(
                amount, consistency, image,
                name, original, unit
            )
        }
    }
}