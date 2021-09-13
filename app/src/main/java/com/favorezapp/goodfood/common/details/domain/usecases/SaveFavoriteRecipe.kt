package com.favorezapp.goodfood.common.details.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import javax.inject.Inject

class SaveFavoriteRecipe @Inject constructor(
    private val repository: RecipesRepository
){
    suspend operator fun invoke( foodRecipe: FoodRecipe ) {
        repository.saveFavoriteRecipe( foodRecipe )
    }
}