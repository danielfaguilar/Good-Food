package com.favorezapp.goodfood.common.data.api.model.mappers

import com.favorezapp.goodfood.common.data.api.model.ApiPaginatedFoodRecipes
import com.favorezapp.goodfood.common.domain.model.pagination.PaginatedFoodRecipes
import com.favorezapp.goodfood.common.domain.model.pagination.Pagination
import javax.inject.Inject

class ApiPaginatedFoodRecipesMapper @Inject constructor(
    private val apiFoodRecipeMapper: ApiFoodRecipeMapper
): ApiMapper<ApiPaginatedFoodRecipes?, PaginatedFoodRecipes> {
    override fun mapToDomain(entity: ApiPaginatedFoodRecipes?): PaginatedFoodRecipes {
        return PaginatedFoodRecipes(
            entity?.foodRecipes?.map {
                apiFoodRecipeMapper.mapToDomain(it)
            }.orEmpty(),
            Pagination(
                entity?.numOfRecipes ?: 0,
                entity?.totalRecipes ?: 0
            )
        )
    }
}