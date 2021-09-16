package com.favorezapp.goodfood.common.details.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.favorezapp.goodfood.common.details.domain.usecases.GetRecipeByTitle
import com.favorezapp.goodfood.common.details.domain.usecases.IsFavoriteRecipe
import com.favorezapp.goodfood.common.details.domain.usecases.RemoveFavRecipeByTitle
import com.favorezapp.goodfood.common.details.domain.usecases.SaveFavoriteRecipe
import com.favorezapp.goodfood.common.details.presentation.DetailsActivityEvent
import com.favorezapp.goodfood.common.details.presentation.DetailsActivityState
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe
import com.favorezapp.goodfood.common.presentation.model.mappers.UIFoodRecipeMapper
import com.favorezapp.goodfood.common.utils.DispatchersProvider
import com.favorezapp.goodfood.common.utils.createExceptionHandler
import com.favorezapp.logging.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val saveFavoriteRecipe: SaveFavoriteRecipe,
    private val isFavoriteRecipe: IsFavoriteRecipe,
    private val getRecipeByTitle: GetRecipeByTitle,
    private val removeFavRecipeByTitle: RemoveFavRecipeByTitle,
    private val uiFoodRecipeMapper: UIFoodRecipeMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    private var _state = MutableLiveData<DetailsActivityState>()
    val state: LiveData<DetailsActivityState> get() = _state
    private lateinit var foodRecipe: FoodRecipe

    var favorite: Boolean = false
    var speaking: Boolean = false

    init {
        _state.value = DetailsActivityState()
    }

    fun onEvent( event: DetailsActivityEvent) {
        when( event ) {
            is DetailsActivityEvent.GetFoodRecipe -> { getFoodRecipe( event.title ) }
            is DetailsActivityEvent.SaveFavoriteRecipe -> { saveRecipe() }
            is DetailsActivityEvent.RemoveFavoriteRecipe -> { removeRecipe() }
        }
    }

    private fun removeRecipe() {
        viewModelScope.launch(dispatchersProvider.io()){
            removeFavRecipeByTitle( foodRecipe.title )
        }
    }

    private fun saveRecipe() {
        viewModelScope.launch( dispatchersProvider.io() ){
            saveFavoriteRecipe( foodRecipe )
        }
    }

    private fun getFoodRecipe( title: String ) {
        val exceptionHandler = viewModelScope
            .createExceptionHandler("Error getting recipe info") {}

        viewModelScope.launch( exceptionHandler ){
            val (recipe, fav) =  withContext(dispatchersProvider.io()) {
                getRecipeByTitle( title ) to isFavoriteRecipe( title )
            }

            foodRecipe = recipe
            favorite = fav

            onRecipeInfoObtained(
                uiFoodRecipeMapper.mapToView( foodRecipe ),
                favorite
            )
        }
    }

    private fun onRecipeInfoObtained( uiRecipe: UIFoodRecipe, favorite: Boolean ) {
        _state.value = state.value!!.copy(
            foodRecipe = uiRecipe,
            isFavorite = favorite
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}