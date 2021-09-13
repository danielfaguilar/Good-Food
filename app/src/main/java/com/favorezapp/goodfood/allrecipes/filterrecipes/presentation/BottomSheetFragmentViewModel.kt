package com.favorezapp.goodfood.allrecipes.filterrecipes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.favorezapp.goodfood.common.utils.DispatchersProvider
import com.favorezapp.goodfood.common.utils.createExceptionHandler
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.DietType
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealAndDietType
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealType
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.usecases.GetMealAndDietType
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.usecases.SaveMealAndDietType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetFragmentViewModel @Inject constructor(
    private val getMealAndDietTypeCase: GetMealAndDietType,
    private val saveMealAndDietTypeCase: SaveMealAndDietType,
    private val compositeDisposable: CompositeDisposable,
    private val dispatchersProvider: DispatchersProvider
): ViewModel() {
    private var _state = MutableLiveData<BottomSheetFragmentViewState>()
    val state: LiveData<BottomSheetFragmentViewState>
        get() = _state

    private var mealType: MealType = MealType.MAIN_COURSE
    private var mealTypeId: Int = 0
    private var dietType: DietType= DietType.GLUTEN_FREE
    private var dietTypeId: Int = 0

    private lateinit var retrieveMealAndDietTypeJob: Job

    init {
        _state.value = BottomSheetFragmentViewState()
    }

    fun onEvent( event: BottomSheetFragmentEvent ){
        when( event ) {
            is BottomSheetFragmentEvent.SaveMealAndDietType -> saveMealAndDietType()
            is BottomSheetFragmentEvent.DietTypeSelected -> updateDietType( event.resId, event.dietType )
            is BottomSheetFragmentEvent.MealTypeSelected -> updateMealType( event.resId, event.mealType )
            is BottomSheetFragmentEvent.PrepareForChoose -> prepareForChoose()
        }
    }

    private fun prepareForChoose() {
        val exceptionHandler = viewModelScope
            .createExceptionHandler("Something went wrong getting meal and diet type") {
                it.printStackTrace()
            }

        retrieveMealAndDietTypeJob = viewModelScope.launch( exceptionHandler ) {
            getMealAndDietTypeCase().collect {
                updateMealAndDietType( it )
            }
        }
    }

    private fun updateMealType(resId: Int, mealTypeReceived: String) {
        mealTypeId = resId
        mealType = MealType.valueOf( mealTypeReceived.replace(' ', '_').uppercase() )
    }

    private fun updateDietType(resId: Int, dietTypeReceived: String) {
        dietTypeId = resId
        dietType = DietType.valueOf( dietTypeReceived.replace(' ', '_').uppercase() )
    }

    private fun saveMealAndDietType() {
        val exceptionHandler = viewModelScope
            .createExceptionHandler("Something went wrong saving meal and diet type") {
                it.printStackTrace()
            }

        viewModelScope.launch( exceptionHandler ) {
            saveMealAndDietTypeCase(
                MealAndDietType(
                    mealType = mealType,
                    mealTypeId = mealTypeId,
                    dietType = dietType,
                    dietTypeId = dietTypeId
                )
            )
        }
    }

    private fun updateMealAndDietType(types: MealAndDietType) {
        _state.value = state.value!!.copy(
            mealAndDietType = types
        )
    }

    override fun onCleared() {
        super.onCleared()
        retrieveMealAndDietTypeJob.cancel()
        compositeDisposable.clear()
    }
}