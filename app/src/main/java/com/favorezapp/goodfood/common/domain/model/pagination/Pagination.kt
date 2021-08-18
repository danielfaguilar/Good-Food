package com.favorezapp.goodfood.common.domain.model.pagination

data class Pagination(
    val numOfRecipes: Int,
    val totalRecipes: Int
) {

    companion object {
        // For the cases when we store the current page locally, but haven't yet requested a new page
        // from the remote source. Total recipes should change with time, so we'll handle the value as
        // unknown before updating.
        const val UNKNOWN_TOTAL = -1
        const val DEFAULT_NUM_OF_RECIPES = 5
    }

    val canLoadMore: Boolean
        get() = numOfRecipes == UNKNOWN_TOTAL || numOfRecipes < totalRecipes
}