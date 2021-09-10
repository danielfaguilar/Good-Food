package com.favorezapp.goodfood.common.data.api

import com.favorezapp.goodfood.common.data.api.model.ApiPaginatedFoodRecipes
import com.favorezapp.goodfood.foodjoke.data.api.model.ApiFoodJoke
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SpooncularApi {
    @GET(ApiConstants.RECIPES_ENDPOINT)
    suspend fun getRecipesByParameters(
        @QueryMap queryParams: Map<String, String>,
        @Query(ApiParameters.NUMBER_PARAM) numOfItems: Int,
        @Query(ApiParameters.TYPE_PARAM) mealType: String,
        @Query(ApiParameters.DIET_PARAM) dietType: String
    ): ApiPaginatedFoodRecipes

    @GET(ApiConstants.RECIPES_ENDPOINT)
    suspend fun searchRecipes(
        @QueryMap queryParams: Map<String, String>,
        @Query(ApiParameters.NUMBER_PARAM) numOfItems: Int,
        @Query(ApiParameters.TYPE_PARAM) mealType: String,
        @Query(ApiParameters.DIET_PARAM) dietType: String,
        @Query(ApiParameters.QUERY_PARAM) query: String
    ): ApiPaginatedFoodRecipes

    @GET(ApiConstants.FOOD_JOKE_ENDPOINT)
    suspend fun getRandomJoke(): ApiFoodJoke
}