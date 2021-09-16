package com.favorezapp.goodfood.allrecipes.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.domain.model.network.NetworkResponseState
import com.favorezapp.goodfood.common.domain.model.pagination.PaginatedFoodRecipes
import com.favorezapp.goodfood.common.domain.model.pagination.Pagination
import javax.inject.Inject

class RequestNextFoodRecipes @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(
        numOfItems: Int = Pagination.DEFAULT_NUM_OF_RECIPES
    ): NetworkResponseState<PaginatedFoodRecipes> {

        val state = repository.requestMoreFoodRecipes( numOfItems )

        if( state is NetworkResponseState.Success<PaginatedFoodRecipes> ) {
            val (recipes, pagination) = state.result

            if( recipes.isNotEmpty() ) {
                repository.deleteAllRecipes()
                repository.storeFoodRecipes(recipes)
            }
        }

        return state
    }
}