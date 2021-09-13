package com.favorezapp.goodfood.allrecipes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.favorezapp.goodfood.allrecipes.domain.usecases.GetLocalRecipes
import com.favorezapp.goodfood.allrecipes.domain.usecases.RequestNextFoodRecipes
import com.favorezapp.goodfood.allrecipes.domain.usecases.SearchAndGetRecipes
import com.favorezapp.goodfood.common.domain.model.NetworkException
import com.favorezapp.goodfood.common.domain.model.NetworkUnavailableException
import com.favorezapp.goodfood.common.domain.model.NoMoreRecipesException
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.domain.model.pagination.Pagination
import com.favorezapp.goodfood.common.presentation.util.Event
import com.favorezapp.goodfood.common.presentation.model.mappers.UIFoodRecipeMapper
import com.favorezapp.goodfood.common.utils.DispatchersProvider
import com.favorezapp.goodfood.common.utils.createExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllRecipesFragmentViewModel @Inject constructor(
    private val requestNextFoodRecipes: RequestNextFoodRecipes,
    private val getLocalRecipes: GetLocalRecipes,
    private val searchAndGetRecipes: SearchAndGetRecipes,
    private val uiFoodRecipeMapper: UIFoodRecipeMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {
    private val _state = MutableLiveData<AllRecipesFragmentViewState>()
    val state: LiveData<AllRecipesFragmentViewState> get() = _state

    private var numberOfRecipes = 0
    private var _hasInternetConnection = false
    val hasInternetConnection: Boolean get( ) = _hasInternetConnection

    init {
        _state.value = AllRecipesFragmentViewState()
    }

    fun onEvent(event: AllRecipesEvent) =
        when( event ) {
            is AllRecipesEvent.RequestInitialRecipes -> { loadRecipes() }
            is AllRecipesEvent.RequestNewRecipes -> { requestNewRecipes() }
            is AllRecipesEvent.OnNetworkStatusChanged -> { updateNetworkStatus( event.netStatus ) }
            is AllRecipesEvent.OnSearchInputSubmit -> { searchQuery( event.query ) }
            is AllRecipesEvent.RequestLocalRecipes -> { requestLocalRecipes() }
        }

    private fun requestLocalRecipes() {
        val exceptionHandler = createExceptionHandler("Error getting local recipes")

        viewModelScope.launch(exceptionHandler) {
            val recipes =  withContext(dispatchersProvider.io()){
                getLocalRecipes()
            }

            onLocalRecipesReceived(recipes)
        }
    }

    private fun onLocalRecipesReceived(foodRecipes: List<FoodRecipe>) {

        val uiRecipes = foodRecipes.map {
            uiFoodRecipeMapper.mapToView( it )
        }

        _state.value = state.value!!.copy(
            loading = false,
            recipes = uiRecipes
        )
    }

    private fun searchQuery(query: String) {
        val exceptionHandler = createExceptionHandler("No recipes found for $query")

        _state.value = state.value!!.updateToLoading()

        viewModelScope.launch(exceptionHandler) {
            val paginatedRecipes = withContext(dispatchersProvider.io()) {
                searchAndGetRecipes( query )
            }

            onNewRecipesList( paginatedRecipes.recipes )
            onPaginationInfoObtained( paginatedRecipes.pagination )
        }
    }

    private fun updateNetworkStatus(netStatus: Boolean) {
        _hasInternetConnection = netStatus

        _state.value = state.value!!.copy(
            internetConnection = netStatus
        )
    }

    private fun requestNewRecipes() {
        loadNextRecipes()
    }

    private fun onNewRecipesList(it: List<FoodRecipe>) {
        val updatedRecipes = it.map {
            uiFoodRecipeMapper.mapToView(it)
        }

        _state.value = state.value!!.copy( loading = false, recipes = updatedRecipes )
    }

    private fun loadRecipes() {
        if( state.value!!.recipes.isEmpty() )
            loadNextRecipes()
    }

    private fun loadNextRecipes() {
        val exceptionHandler = createExceptionHandler("Error loading recipes")

        viewModelScope.launch(exceptionHandler) {
            val paginatedRecipes = withContext(dispatchersProvider.io()) {
                requestNextFoodRecipes()
            }

            onNewRecipesList( paginatedRecipes.recipes )
            onPaginationInfoObtained( paginatedRecipes.pagination )
        }
    }

    private fun onPaginationInfoObtained(pagination: Pagination) {
        numberOfRecipes = pagination.numOfRecipes
    }

    private fun createExceptionHandler(message: String): CoroutineExceptionHandler =
        viewModelScope.createExceptionHandler(message) { onFailure(it) }

    private fun onFailure(failure: Throwable) {
        when( failure ) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _state.value = state.value!!.copy(
                    loading = false,
                    failure = Event(failure)
                )
            }
            is NoMoreRecipesException -> {
                _state.value = state.value!!.copy(
                    loading = false,
                    failure = Event(failure),
                    recipes = emptyList(),
                    noMoreRecipes = true
                )
            }
            else -> {
                _state.value = state.value!!.copy(
                    loading = false,
                    recipes = emptyList(),
                    failure = Event(failure)
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}