package com.favorezapp.goodfood.favoritesrecipes.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import javax.inject.Inject

class DeleteFavoriteRecipeByTitle @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke( title: String ){
        repository.deleteFavoriteFoodRecipeByTitle( title )
    }
}