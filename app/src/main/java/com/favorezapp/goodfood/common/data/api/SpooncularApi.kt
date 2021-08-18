package com.favorezapp.goodfood.common.data.api

import com.favorezapp.goodfood.common.data.api.model.ApiPaginationFoodRecipes
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SpooncularApi {
    @GET(ApiConstants.RECIPES_ENDPOINT)
    suspend fun getRecipesByParameters(
        @QueryMap queryParams: Map<String, String>
    ): ApiPaginationFoodRecipes
}