package com.favorezapp.goodfood.allrecipes.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import javax.inject.Inject

class GetLocalRecipes @Inject constructor(
    private val repository: RecipesRepository
){
    suspend operator fun invoke(): List<FoodRecipe> {
        return repository.getLocalRecipes()
    }
}