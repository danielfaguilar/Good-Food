package com.favorezapp.goodfood.common.data

import com.favorezapp.goodfood.common.data.api.SpooncularApi
import com.favorezapp.goodfood.common.data.api.model.mappers.ApiFoodRecipeMapper
import com.favorezapp.goodfood.common.data.cache.Cache
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFoodRecipeAggregate
import com.favorezapp.goodfood.common.domain.RecipesRepository
import com.favorezapp.goodfood.common.domain.model.NoMoreRecipesException
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.domain.model.pagination.PaginatedFoodRecipes
import com.favorezapp.goodfood.common.domain.model.pagination.Pagination
import com.favorezapp.goodfood.allrecipes.filterrecipes.data.preferences.DataStorePreferences
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealAndDietType
import com.favorezapp.goodfood.common.data.cache.models.foodrecipe.CachedFavoriteFoodRecipe
import com.favorezapp.goodfood.foodjoke.data.api.model.mappers.ApiFoodJokeMapper
import com.favorezapp.goodfood.foodjoke.data.cache.model.CachedFoodJoke
import com.favorezapp.goodfood.foodjoke.domain.model.FoodJoke
import io.reactivex.Flowable
import kotlinx.coroutines.flow.*
import java.lang.NullPointerException
import javax.inject.Inject

class SpooncularRecipesRepository @Inject constructor(
    private val cache: Cache,
    private val api: SpooncularApi,
    private val preferences: DataStorePreferences,
    private val apiFoodRecipeMapper: ApiFoodRecipeMapper,
    private val apiFoodJokeMapper: ApiFoodJokeMapper
): RecipesRepository {
    override suspend fun requestMoreFoodRecipes(numOfItems: Int ): PaginatedFoodRecipes {
        val emptyParams = mapOf<String, String>()

        val mealAndDietType = preferences.getMealAndDietType().firstOrNull()
            ?: throw NullPointerException("")

        val mealType = mealAndDietType.mealType
        val dietType = mealAndDietType.dietType

        val (recipes, numOfRecipes, totalRecipes) = api
            .getRecipesByParameters(
                emptyParams,
                numOfItems,
                mealType.toString().lowercase().replace("_", " "),
                dietType.toString().lowercase().replace("_", " ")
            )

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
            .map { foodRecipeList ->
                foodRecipeList.map {
                    it.foodRecipe.toDomain(it.cuisines, it.extendedIngredients)
                }
            }
    }

    override suspend fun getLocalRecipes(): List<FoodRecipe> {
        return cache
            .getLocalFoodRecipes()
            .map {
                it.foodRecipe.toDomain(
                    it.cuisines,
                    it.extendedIngredients
                )
            }
    }

    override suspend fun searchAndGetRecipes(numOfItems: Int, query: String): PaginatedFoodRecipes {
        val emptyParams = mapOf<String, String>()

        val mealAndDietType = preferences.getMealAndDietType().firstOrNull()
            ?: throw NullPointerException("")

        val mealType = mealAndDietType.mealType
        val dietType = mealAndDietType.dietType

        val (recipes, numOfRecipes, totalRecipes) = api
            .searchRecipes(
                emptyParams,
                numOfItems,
                mealType.toString().lowercase().replace("_", " "),
                dietType.toString().lowercase().replace("_", " "),
                query
            )

        val domainRecipes = recipes?.map {
            apiFoodRecipeMapper.mapToDomain( it )
        }.orEmpty()

        return PaginatedFoodRecipes(
            domainRecipes,
            Pagination(
                numOfRecipes ?: 0,
                totalRecipes ?: 0
            )
        )
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
                it.foodRecipe.toDomain(it.cuisines, it.extendedIngredients)
            }
    }

    override suspend fun saveFavoriteRecipe(foodRecipe: FoodRecipe) {
        cache.saveFavoriteRecipe( CachedFavoriteFoodRecipe.fromDomain(foodRecipe) )
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

    override suspend fun requestFoodJoke(): FoodJoke {
        return apiFoodJokeMapper.mapTo(api.getRandomJoke())
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
        cache.storeFoodJoke( CachedFoodJoke.fromDomain(joke) )
    }
}