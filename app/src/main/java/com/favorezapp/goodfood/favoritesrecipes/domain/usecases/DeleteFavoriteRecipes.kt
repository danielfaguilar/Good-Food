package com.favorezapp.goodfood.favoritesrecipes.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import javax.inject.Inject

class DeleteFavoriteRecipes @Inject constructor(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(){
        repository.deleteAllFavoriteFoodRecipes()
    }
}