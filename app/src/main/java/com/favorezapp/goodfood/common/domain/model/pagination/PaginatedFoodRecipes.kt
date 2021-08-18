package com.favorezapp.goodfood.common.domain.model.pagination

import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe

data class PaginatedFoodRecipes(
    val recipes: List<FoodRecipe>,
    val pagination: Pagination
)