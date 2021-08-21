package com.favorezapp.goodfood.common.domain

import com.favorezapp.goodfood.common.domain.model.pagination.PaginatedFoodRecipes

interface RecipesRepository {
    suspend fun requestMoreFoodRecipes(): PaginatedFoodRecipes
}