package com.favorezapp.goodfood.favoritesrecipes.domain.usecases

import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import io.reactivex.Flowable
import javax.inject.Inject

class GetFavoriteRecipes @Inject constructor(
    private val repository: RecipesRepository
) {
    operator fun invoke(): Flowable<List<FoodRecipe>> {
        return repository
            .getFavoriteRecipes()
            .distinctUntilChanged()
    }
}