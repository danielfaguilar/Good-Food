package com.favorezapp.goodfood.allrecipes.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import javax.inject.Inject

class GetRecipes @Inject constructor(
    private val repository: RecipesRepository
) {
    operator fun invoke() = repository.getRecipes().filter { it.isNotEmpty() }
}