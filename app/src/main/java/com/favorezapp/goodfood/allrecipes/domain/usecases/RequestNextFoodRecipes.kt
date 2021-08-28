package com.favorezapp.goodfood.allrecipes.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.domain.model.NoMoreRecipesException
import com.favorezapp.goodfood.common.domain.model.pagination.Pagination
import javax.inject.Inject

class RequestNextFoodRecipes @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(): Pagination {
        val (recipes, pagination) = repository
            .requestMoreFoodRecipes()

        if( recipes.isEmpty() )
            throw NoMoreRecipesException()

        repository.storeFoodRecipes(recipes)

        return pagination
    }
}