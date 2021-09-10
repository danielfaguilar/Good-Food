package com.favorezapp.goodfood.allrecipes.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.domain.model.NoMoreRecipesException
import com.favorezapp.goodfood.common.domain.model.pagination.PaginatedFoodRecipes
import com.favorezapp.goodfood.common.domain.model.pagination.Pagination
import javax.inject.Inject

class RequestNextFoodRecipes @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(
        numOfItems: Int = Pagination.DEFAULT_NUM_OF_RECIPES
    ): PaginatedFoodRecipes {
        val (recipes, pagination) = repository
            .requestMoreFoodRecipes( numOfItems )

        if( recipes.isEmpty() )
            throw NoMoreRecipesException()

        repository.deleteAllRecipes()
        repository.storeFoodRecipes(recipes)

        return PaginatedFoodRecipes( recipes, pagination )
    }
}