package com.favorezapp.goodfood.allrecipes.filterrecipes.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealAndDietType
import javax.inject.Inject

class SaveMealAndDietType @Inject constructor(
    private val repository: RecipesRepository
){
    suspend operator fun invoke(mealAndDietType: MealAndDietType) {
        repository.saveMealAndDietType( mealAndDietType )
    }
}