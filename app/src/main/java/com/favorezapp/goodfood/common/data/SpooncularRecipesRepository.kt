package com.favorezapp.goodfood.common.data

import com.favorezapp.goodfood.common.data.api.SpooncularApi
import com.favorezapp.goodfood.common.data.api.model.mappers.ApiFoodRecipeMapper
import com.favorezapp.goodfood.common.data.api.model.mappers.ApiPaginatedFoodRecipesMapper
import com.favorezapp.goodfood.common.data.cache.Cache
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFoodRecipeAggregate
import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.domain.model.NoMoreRecipesException
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.domain.model.pagination.PaginatedFoodRecipes
import com.favorezapp.goodfood.common.domain.model.pagination.Pagination
import io.reactivex.Flowable
import javax.inject.Inject

class SpooncularRecipesRepository @Inject constructor(
    private val api: SpooncularApi,
    private val cache: Cache,
    private val apiFoodRecipeMapper: ApiFoodRecipeMapper,
    private val apiPaginationFoodRecipesMapper: ApiPaginatedFoodRecipesMapper
): RecipesRepository {
    override suspend fun requestMoreFoodRecipes(): PaginatedFoodRecipes {
        val emptyParams = mapOf<String, String>()

        val (recipes, numOfRecipes, totalRecipes) = api
            .getRecipesByParameters(emptyParams)

        if(recipes?.isEmpty() == true)
            throw NoMoreRecipesException("No recipes available")

        return PaginatedFoodRecipes(
            recipes?.map {
                apiFoodRecipeMapper.mapToDomain(it)
            }.orEmpty(),
            Pagination(
                numOfRecipes ?: 0,
                totalRecipes ?: 0
            )
        )
    }

    override suspend fun storeFoodRecipes(foodRecipes: List<FoodRecipe>) {
        cache.storeFoodRecipes(
            foodRecipes.map {
                CachedFoodRecipeAggregate.fromDomain(it)
            }
        )
    }

    override fun getRecipes(): Flowable<List<FoodRecipe>> {
        return cache
            .getFoodRecipes()
            .distinctUntilChanged()
            .map { foodRecipeList ->
                foodRecipeList.map {
                    it.foodRecipe.toDomain(it.cuisines, it.extendedIngredients)
                }
            }
    }
}