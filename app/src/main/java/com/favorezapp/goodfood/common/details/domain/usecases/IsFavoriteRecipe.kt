package com.favorezapp.goodfood.common.details.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import javax.inject.Inject

class IsFavoriteRecipe @Inject constructor(
    private val repository: RecipesRepository
){
    suspend operator fun invoke( title: String ): Boolean {
        return repository.isFavoriteRecipe(title)
    }
}