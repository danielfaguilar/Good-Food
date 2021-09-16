package com.favorezapp.goodfood.common.data

import com.favorezapp.goodfood.common.data.api.SpooncularApi
import com.favorezapp.goodfood.common.data.api.model.mappers.ApiFoodRecipeMapper
import com.favorezapp.goodfood.common.data.cache.Cache
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFoodRecipeAggregate
import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.domain.model.pagination.PaginatedFoodRecipes
import com.favorezapp.goodfood.common.domain.model.pagination.Pagination
import com.favorezapp.goodfood.allrecipes.filterrecipes.data.preferences.DataStorePreferences
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealAndDietType
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFavoriteFoodRecipe
import com.favorezapp.goodfood.common.domain.model.network.NetworkResponseState
import com.favorezapp.goodfood.foodjoke.data.api.model.mappers.ApiFoodJokeMapper
import com.favorezapp.goodfood.foodjoke.data.cache.model.CachedFoodJoke
import com.favorezapp.goodfood.foodjoke.domain.model.FoodJoke
import io.reactivex.Flowable
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.lang.NullPointerException
import javax.inject.Inject

class SpooncularRecipesRepository @Inject constructor(
    private val cache: Cache,
    private val api: SpooncularApi,
    private val preferences: DataStorePreferences,
    private val apiFoodRecipeMapper: ApiFoodRecipeMapper,
    private val apiFoodJokeMapper: ApiFoodJokeMapper
): RecipesRepository {
    override suspend fun requestMoreFoodRecipes(numOfItems: Int): NetworkResponseState<PaginatedFoodRecipes> {
        val emptyParams = mapOf<String, String>()

        val mealAndDietType = preferences.getMealAndDietType().firstOrNull()
            ?: throw NullPointerException("")

        val mealType = mealAndDietType.mealType
        val dietType = mealAndDietType.dietType

        return try {
            val response = api.getRecipesByParameters(
                emptyParams,
                numOfItems,
                mealType.toString().lowercase().replace("_", " "),
                dietType.toString().lowercase().replace("_", " ")
            )
            if (response.isSuccessful)
                if (response.body() != null) {
                    val (recipes, numOfRecipes, totalRecipes) = response.body()!!
                    val domainRecipes = recipes.orEmpty().map {
                        apiFoodRecipeMapper.mapToDomain(it)
                    }
                    val paginatedFoodRecipes = PaginatedFoodRecipes(
                        domainRecipes, Pagination(numOfRecipes ?: 0, totalRecipes ?: 0)
                    )
                    NetworkResponseState.Success(paginatedFoodRecipes)
                } else
                    NetworkResponseState.InvalidData()
            else
                getHttpError(response.code(), response.message())

        } catch (e: IOException) {
            NetworkResponseState.NetworkException(e.message!!)
        }
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
            .map { foodRecipeList ->
                foodRecipeList.map {
                    it.foodRecipe.toDomain(
                        it.cuisines,
                        it.extendedIngredients,
                        it.analyzedInstructions
                    )
                }
            }
    }

    override suspend fun getLocalRecipes(): List<FoodRecipe> {
        return cache
            .getLocalFoodRecipes()
            .map {
                it.foodRecipe.toDomain(
                    it.cuisines,
                    it.extendedIngredients,
                    it.analyzedInstructions
                )
            }
    }

    override suspend fun searchAndGetRecipes(numOfItems: Int, query: String):
            NetworkResponseState<PaginatedFoodRecipes> {
        val emptyParams = mapOf<String, String>()

        val mealAndDietType = preferences.getMealAndDietType().firstOrNull()
            ?: throw NullPointerException("")

        val mealType = mealAndDietType.mealType
        val dietType = mealAndDietType.dietType

        return try {
            val response = api.searchRecipes(
                emptyParams,
                numOfItems,
                mealType.toString().lowercase().replace("_", " "),
                dietType.toString().lowercase().replace("_", " "),
                query
            )

            if (response.isSuccessful)
                if (response.body() != null) {
                    val (recipes, numOfRecipes, totalRecipes) = response.body()!!

                    val domainRecipes = recipes.orEmpty().map {
                        apiFoodRecipeMapper.mapToDomain(it)
                    }

                    val paginatedRecipes = PaginatedFoodRecipes(
                        domainRecipes,
                        Pagination(
                            numOfRecipes ?: 0,
                            totalRecipes ?: 0
                        )
                    )

                    NetworkResponseState.Success(paginatedRecipes)
                } else
                    NetworkResponseState.InvalidData()
            else
                getHttpError(response.code(), response.message())

        } catch (e: IOException) {
            NetworkResponseState.NetworkException(e.message!!)
        }
    }

    override fun getMealAndDietType(): Flow<MealAndDietType> {
        return preferences.getMealAndDietType()
    }

    override suspend fun saveMealAndDietType(types: MealAndDietType) {
        preferences.saveMealAndDietType(
            mealTypeId = types.mealTypeId,
            mealType = types.mealType,
            dietTypeId = types.dietTypeId,
            dietType = types.dietType
        )
    }

    override suspend fun deleteAllRecipes() {
        cache.deleteAllRecipes()
    }

    override suspend fun getFoodRecipeByTitle(title: String): FoodRecipe {
        return cache
            .getFoodRecipeByTitle(title).let {
                it.foodRecipe.toDomain(it.cuisines, it.extendedIngredients, it.analyzedInstructions)
            }
    }

    override suspend fun saveFavoriteRecipe(foodRecipe: FoodRecipe) {
        cache.saveFavoriteRecipe(CachedFavoriteFoodRecipe.fromDomain(foodRecipe))
    }

    override fun getFavoriteRecipes(): Flowable<List<FoodRecipe>> {
        return cache
            .getFavoriteRecipes()
            .map { list ->
                list.map {
                    it.toDomain()
                }
            }
    }

    override suspend fun isFavoriteRecipe(title: String): Boolean {
        return cache.isFavoriteRecipe(title)
    }

    override suspend fun deleteAllFavoriteFoodRecipes() {
        cache.deleteAllFavoriteRecipes()
    }

    override suspend fun deleteFavoriteFoodRecipeByTitle(title: String) {
        cache.deleteFavoriteRecipeById(title)
    }

    override suspend fun requestFoodJoke(): NetworkResponseState<FoodJoke> {
        return try {
            val response = api.getRandomJoke()

            if (response.isSuccessful)
                if (response.body() != null)
                    NetworkResponseState.Success(
                        apiFoodJokeMapper.mapTo(response.body()!!)
                    )
                else
                    NetworkResponseState.InvalidData()
            else
                getHttpError(response.code(), response.message())

        } catch (e: IOException) {
            NetworkResponseState.NetworkException(e.message!!)
        }
    }

    private fun <T> getHttpError(code: Int, message: String): NetworkResponseState<T> {
        return when (code) {
            401 -> NetworkResponseState.HttpError
                .InvalidCredentials(message)

            403 -> NetworkResponseState.HttpError
                .ResourceForbidden(message)

            404 -> NetworkResponseState.HttpError
                .ResourceNotFound(message)

            500 -> NetworkResponseState.HttpError
                .InternalServerError(message)

            502 -> NetworkResponseState.HttpError
                .BadGateWay(message)

            301 -> NetworkResponseState.HttpError
                .ResourceRemoved(message)

            302 -> NetworkResponseState.HttpError
                .RemovedResourceFound(message)

            else -> NetworkResponseState.Error(message)
        }
    }

    override suspend fun deleteFoodJoke() {
        cache.deleteFoodJoke()
    }

    override fun getFoodJoke(): Flowable<FoodJoke> {
        return cache.getFoodJoke().map {
            it.toDomain()
        }
    }

    override suspend fun storeFoodJoke(joke: FoodJoke) {
        cache.storeFoodJoke(CachedFoodJoke.fromDomain(joke))
    }
}