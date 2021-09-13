package com.favorezapp.goodfood.allrecipes.filterrecipes.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealAndDietType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetMealAndDietType @Inject constructor(
    private val repository: RecipesRepository
) {
    operator fun invoke(): Flow<MealAndDietType> {
        return repository.getMealAndDietType()
    }
}