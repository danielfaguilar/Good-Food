package com.favorezapp.goodfood.allrecipes.filterrecipes.data.preferences

import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.DietType
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealAndDietType
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealType
import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {
    suspend fun saveMealAndDietType(
        mealTypeId: Int, mealType: MealType,
        dietTypeId: Int, dietType: DietType
    )
    fun getMealAndDietType(): Flow<MealAndDietType>
}