package com.favorezapp.goodfood.allrecipes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.favorezapp.goodfood.allrecipes.domain.usecases.GetLocalRecipes
import com.favorezapp.goodfood.allrecipes.domain.usecases.RequestNextFoodRecipes
import com.favorezapp.goodfood.allrecipes.domain.usecases.SearchAndGetRecipes
import com.favorezapp.goodfood.common.domain.model.foodrecipe.FoodRecipe
import com.favorezapp.goodfood.common.domain.model.network.NetworkResponseState
import com.favorezapp.goodfood.common.domain.model.pagination.PaginatedFoodRecipes
import com.favorezapp.goodfood.common.domain.model.pagination.Pagination
import com.favorezapp.goodfood.common.presentation.util.Event
import com.favorezapp.goodfood.common.presentation.model.mappers.UIFoodRecipeMapper
import com.favorezapp.goodfood.common.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
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
            is AllRecipesEvent.RequestLocalRecipes -> {
                requestLocalRecipes()
            }
        }

    private fun requestLocalRecipes() {
        viewModelScope.launch {
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
        _state.value = state.value!!.copy(
            loading = true
        )

        viewModelScope.launch {
            val networkResponse = withContext(dispatchersProvider.io()) {
                searchAndGetRecipes( query )
            }
            onNetworkResponseObtained( networkResponse )
        }
    }

    private fun updateNetworkStatus(netStatus: Boolean) {
        _hasInternetConnection = netStatus
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
        _state.value = state.value!!.copy( loading = true )

        viewModelScope.launch {
            val networkResponse = withContext(dispatchersProvider.io()) {
                requestNextFoodRecipes()
            }
            onNetworkResponseObtained( networkResponse )
        }
    }

    private fun onPaginationInfoObtained(pagination: Pagination) {
        numberOfRecipes = pagination.numOfRecipes
    }

    private fun onNetworkResponseObtained( response: NetworkResponseState<*> ) {
        when( response ) {
            is NetworkResponseState.Error ->
                onFailureWithMessage( response.message )

            is NetworkResponseState.HttpError.BadGateWay ->
                onFailureWithCode( NetworkResponseState.HttpError.BadGateWay.CODE )

            is NetworkResponseState.HttpError.InternalServerError ->
                onFailureWithCode( NetworkResponseState.HttpError.InternalServerError.CODE )

            is NetworkResponseState.HttpError.RemovedResourceFound ->
                onFailureWithCode( NetworkResponseState.HttpError.RemovedResourceFound.CODE )

            is NetworkResponseState.HttpError.ResourceForbidden ->
                onFailureWithCode( NetworkResponseState.HttpError.ResourceForbidden.CODE )

            is NetworkResponseState.HttpError.ResourceNotFound ->
                onFailureWithCode( NetworkResponseState.HttpError.ResourceNotFound.CODE )

            is NetworkResponseState.HttpError.ResourceRemoved ->
                onFailureWithCode( NetworkResponseState.HttpError.ResourceRemoved.CODE )

            is NetworkResponseState.InvalidData ->
                onFailureWithCode( NetworkResponseState.InvalidData.CODE )

            is NetworkResponseState.NetworkException ->
                onFailureWithCode( NetworkResponseState.NetworkException.CODE )

            is NetworkResponseState.Success -> {
                val paginatedRecipes = response.result as PaginatedFoodRecipes

                if( paginatedRecipes.recipes.isEmpty() ) {
                    _state.value = state.value!!.copy(
                        loading = false,
                        noMoreRecipes = true,
                        errorCode = Event( NetworkResponseState.HttpError.ResourceNotFound.CODE )
                    )
                    return
                }

                onNewRecipesList( paginatedRecipes.recipes )
                onPaginationInfoObtained( paginatedRecipes.pagination )
            }
            is NetworkResponseState.HttpError.InvalidCredentials ->
                onFailureWithCode( NetworkResponseState.HttpError.InvalidCredentials.CODE )
            else ->
                onFailureWithCode( NetworkResponseState.Error.CODE )
        }
    }

    private fun onFailureWithCode(code: Int) {
        _state.value = state.value!!.copy(
            loading = false,
            errorCode = Event(code)
        )
    }

    private fun onFailureWithMessage(message: String) {
        _state.value = state.value!!.copy(
            loading = false,
            errorMessage = Event( message )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}