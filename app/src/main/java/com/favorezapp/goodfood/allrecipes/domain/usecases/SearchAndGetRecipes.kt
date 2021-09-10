package com.favorezapp.goodfood.allrecipes.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.domain.model.NoMoreRecipesException
import com.favorezapp.goodfood.common.domain.model.pagination.PaginatedFoodRecipes
import com.favorezapp.goodfood.common.domain.model.pagination.Pagination
import javax.inject.Inject

class SearchAndGetRecipes @Inject constructor(
    private val repository: RecipesRepository
){
    suspend operator fun invoke(
        query: String,
        numOfItems: Int = Pagination.DEFAULT_NUM_OF_RECIPES
    ): PaginatedFoodRecipes {
        val (recipes, pagination) = repository.searchAndGetRecipes(numOfItems, query)

        repository.deleteAllRecipes()

        if( recipes.isNullOrEmpty() )
            throw NoMoreRecipesException("No recipes found for $query")

        repository.storeFoodRecipes( recipes )

        return PaginatedFoodRecipes( recipes, pagination )
    }
}