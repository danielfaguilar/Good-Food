package com.favorezapp.goodfood.favoritesrecipes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.presentation.model.mappers.UIFoodRecipeMapper
import com.favorezapp.goodfood.common.utils.DispatchersProvider
import com.favorezapp.goodfood.common.utils.createExceptionHandler
import com.favorezapp.goodfood.favoritesrecipes.domain.usecases.DeleteFavoriteRecipes
import com.favorezapp.goodfood.favoritesrecipes.domain.usecases.DeleteFavoriteRecipeByTitle
import com.favorezapp.goodfood.favoritesrecipes.domain.usecases.GetFavoriteRecipes
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipesViewModel @Inject constructor(
    private val deleteAllFavoriteRecipes: DeleteFavoriteRecipes,
    private val getAllFavoriteRecipes: GetFavoriteRecipes,
    private val deleteFavoriteRecipeByTitle: DeleteFavoriteRecipeByTitle,
    private val compositeDisposable: CompositeDisposable,
    private val uiFoodRecipeMapper: UIFoodRecipeMapper,
    private val dispatchersProvider: DispatchersProvider
): ViewModel() {
    private var _state = MutableLiveData<FavoriteRecipesViewState>()
    val state: LiveData<FavoriteRecipesViewState> get() = _state

    init {
        _state.value = FavoriteRecipesViewState()
        subscribeToFavoriteRecipes()
    }

    fun onEvent( event: FavoriteRecipesEvent ) {
        when( event ) {
            is FavoriteRecipesEvent.DeleteFavoriteRecipes -> { deleteFavRecipes() }
            is FavoriteRecipesEvent.DeleteFavoriteRecipeById -> { deleteRecipe( event.recipeTitle ) }
        }
    }

    private fun subscribeToFavoriteRecipes() {
        getAllFavoriteRecipes()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onRecipesObtained(it) },
                { /* Manage errors */ }
            ).addTo( compositeDisposable )
    }

    private fun deleteRecipe(title: String) {
        viewModelScope.launch( dispatchersProvider.io() ){
            deleteFavoriteRecipeByTitle(title)
        }
    }

    private fun onRecipesObtained(recipes: List<FoodRecipe>) {
        val uiRecipes = recipes.map {
            uiFoodRecipeMapper.mapToView(it)
        }
        _state.value = state.value!!.copy(
            favRecipes = uiRecipes
        )
    }

    private fun deleteFavRecipes() {
        val exceptionHandler = viewModelScope
            .createExceptionHandler("Error deleting favorite recipes") {}
        viewModelScope.launch( dispatchersProvider.io() ){
            deleteAllFavoriteRecipes()
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}