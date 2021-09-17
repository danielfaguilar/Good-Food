package com.favorezapp.goodfood.common.data.api.model.mappers

import com.favorezapp.goodfood.common.data.api.model.ApiAnalyzedInstruction
import com.favorezapp.goodfood.common.data.api.model.ApiExtendedIngredient
import com.favorezapp.goodfood.common.data.api.model.ApiFoodRecipe
import com.favorezapp.goodfood.common.domain.model.foodrecipe.AnalyzedInstructions
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import java.lang.NullPointerException
import javax.inject.Inject

class ApiFoodRecipeMapper @Inject constructor(
    private val apiExtendedIngredientsMapper: ApiExtendedIngredientMapper,
    private val analyzedInstructionMapper: ApiAnalyzedInstructionMapper
): ApiMapper<ApiFoodRecipe, FoodRecipe> {
    override fun mapToDomain(entity: ApiFoodRecipe) =
        FoodRecipe(
            entity.id,
            entity.aggregateLikes ?: 0,
            parseAnalyzedInstructions(
                entity.title ?: throw NullPointerException("Recipe title cannot be null")
                , entity.analyzedInstructions
            ),
            entity.author.orEmpty(),
            entity.cheap ?: false,
            entity.cuisines.orEmpty(),
            entity.dairyFree ?: false,
            parseExtendedIngredients(entity.apiExtendedIngredients.orEmpty()),
            entity.glutenFree ?: false,
            entity.image.orEmpty(),
            entity.lowFodmap ?: false,
            entity.readyInMinutes ?: 0,
            entity.sourceName.orEmpty(),
            entity.sourceUrl.orEmpty(),
            entity.summary.orEmpty(),
            entity.title.orEmpty(),
            entity.vegan ?: throw MappingException("VEGAN attr cannot be null"),
            entity.vegetarian ?: false,
            entity.veryHealthy ?: false
        )

    private fun parseAnalyzedInstructions(
        title: String,
        analyzedInstructions: List<ApiAnalyzedInstruction>?
    ): AnalyzedInstructions {
        return AnalyzedInstructions(
            analyzedInstructions.orEmpty().map {
                analyzedInstructionMapper
                    .mapToDomain(it)
                    .copy(recipeTitle = title)
            }
        )
    }

    private fun parseExtendedIngredients(apiExtendedIngredients: List<ApiExtendedIngredient>) =
        apiExtendedIngredients.map {
            apiExtendedIngredientsMapper.mapToDomain(it)
        }
}