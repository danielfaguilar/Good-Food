package com.favorezapp.goodfood.allrecipes.filterrecipes.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.DietType
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealType
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealAndDietType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStorePreferencesTypes @Inject constructor(
    @ApplicationContext private val context: Context
): DataStorePreferences {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    companion object {
        const val PREFERENCES_NAME = "good_food_preferences"
    }

    private object PreferencesKeys {
        const val PREFERENCES_MEAL_TYPE = "preferencesMealType"
        const val PREFERENCES_MEAL_TYPE_ID = "preferencesMealTypeId"
        const val PREFERENCES_DIET_TYPE = "preferencesDietType"
        const val PREFERENCES_DIET_TYPE_ID = "preferencesDietTypeId"

        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }


    private suspend inline fun edit(crossinline block: MutablePreferences.() -> Unit ) {
        with( context.dataStore ) {
            edit {
                it.block()
            }
        }
    }

    override suspend fun saveMealAndDietType(
        mealTypeId: Int, mealType: MealType,
        dietTypeId: Int, dietType: DietType
    ) {

        context.dataStore.edit { prefs ->
            prefs[ PreferencesKeys.selectedMealType ] = mealType.toString()
            prefs[ PreferencesKeys.selectedMealTypeId ] = mealTypeId

            prefs[ PreferencesKeys.selectedDietType ] = dietType.toString()
            prefs[ PreferencesKeys.selectedDietTypeId ] = dietTypeId
        }

    }

    override fun getMealAndDietType(): Flow<MealAndDietType> =
         context.dataStore.data
            .catch { e ->
                if (e is IOException)
                    emit(emptyPreferences())
                else
                    throw e
            }
            .map { prefs ->
                val mealTypeId = prefs[PreferencesKeys.selectedMealTypeId] ?: 0
                val mealType = prefs[PreferencesKeys.selectedMealType] ?: MealType.MAIN_COURSE
                val dietTypeId = prefs[PreferencesKeys.selectedDietTypeId] ?: 0
                val dietType = prefs[PreferencesKeys.selectedDietType] ?: DietType.GLUTEN_FREE

                MealAndDietType(
                    mealType = MealType.valueOf(mealType.toString()),
                    mealTypeId = mealTypeId,
                    dietType = DietType.valueOf(dietType.toString()),
                    dietTypeId = dietTypeId
                )
            }

}