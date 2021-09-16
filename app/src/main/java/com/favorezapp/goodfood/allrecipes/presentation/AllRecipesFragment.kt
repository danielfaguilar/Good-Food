package com.favorezapp.goodfood.allrecipes.presentation

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.favorezapp.goodfood.R
import com.favorezapp.goodfood.common.presentation.util.Event
import com.favorezapp.goodfood.common.presentation.adapters.RecipeAdapter
import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe
import com.favorezapp.goodfood.common.utils.NetworkListener
import com.favorezapp.goodfood.databinding.FragmentAllRecipesBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllRecipesFragment : Fragment(), RecipeAdapter.OnRecipeClickListener {
    private val viewModel: AllRecipesFragmentViewModel by viewModels()
    private val args by navArgs<AllRecipesFragmentArgs>()
    private lateinit var networkListener: NetworkListener
    private var recipes: List<UIFoodRecipe> = emptyList()
    private var recipeQuery: String = ""

    private lateinit var _binding: FragmentAllRecipesBinding
    private val binding: FragmentAllRecipesBinding
        get() = _binding

    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllRecipesBinding.inflate(inflater, container, false)

        setHasOptionsMenu( true )

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNetworkListener()
        setupUI()
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
        setupListeners()
        requestInitialRecipes()
    }

    private fun setupNetworkListener() {
        networkListener = NetworkListener()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                networkListener
                    .checkNetAvailability( requireContext() )
                    .collect { available ->
                        viewModel.onEvent(
                            AllRecipesEvent.OnNetworkStatusChanged( available )
                        )
                    }
            }
        }

    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            if( this::searchView.isInitialized && searchView.query.isNotEmpty() ) {
                searchView.setQuery("", false)
                searchView.clearFocus()
                searchView.isIconified = true
            }
            viewModel.onEvent( AllRecipesEvent.RequestNewRecipes )
        }

        binding.floatingActionButton.setOnClickListener {
            if( viewModel.hasInternetConnection )
                findNavController()
                    .navigate(R.id.action_allFoodRecipesFragment_to_bottomSheetFragment)
            else
                Snackbar
                    .make(
                        binding.constraintLayout,
                        R.string.verify_your_internet_connection,
                        Snackbar.LENGTH_LONG
                    )
                    .show()
        }
    }

    private fun requestInitialRecipes() {
        if( !viewModel.hasInternetConnection )
            viewModel.onEvent( AllRecipesEvent.RequestLocalRecipes )

        else if( args.backFromBottomSheet )
            viewModel.onEvent( AllRecipesEvent.RequestNewRecipes )

        else
            viewModel.onEvent( AllRecipesEvent.RequestInitialRecipes )
    }

    private fun setupRecyclerView(recipeAdapter: RecipeAdapter) {
        binding.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext());

        binding.shimmerRecyclerView.apply {
            adapter = recipeAdapter
        }
    }

    private fun observeViewStateUpdates(adapter: RecipeAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it, adapter)
        }
    }

    private fun updateScreenState(
        state: AllRecipesFragmentViewState,
        adapter: RecipeAdapter
    ) {
        recipes = state.recipes

        binding.shimmerRecyclerView.isVisible = !state.loading
        binding.shimmerFrameLayout.isVisible = state.loading

        if( state.loading ) {
            binding.imageViewNotFound.isVisible = false
            binding.textViewNotFound.isVisible = false
            binding.shimmerFrameLayout.startShimmer()
        }

        else {
            binding.shimmerFrameLayout.stopShimmer()
            adapter.setData(recipes)
        }

        handleInternetConnection( viewModel.hasInternetConnection )
        handleFailureWithCode( state.errorCode )
        handleFailureWithMessage( state.errorMessage )
    }

    private fun handleFailureWithMessage(errorMessage: Event<String>?) {
        val unhandledMessage = errorMessage?.getContentIfNotHandled() ?: return


    }

    private fun handleFailureWithCode(code: Event<Int>?) {
        val unhandledCode = code?.getContentIfNotHandled() ?: return

        val message = when( unhandledCode ) {
            401 -> getString(R.string.http_error_401)
            403 -> getString(R.string.http_error_403)
            404 -> getString(R.string.http_error_404)
            500 -> getString(R.string.http_error_500)
            502 -> getString(R.string.http_error_502)
            301 -> getString(R.string.http_error_301)
            302 -> getString(R.string.http_error_302)
            0 -> getString(R.string.invalid_data_error)
            2 -> getString(R.string.network_error)
            else -> getString(R.string.an_error_occurred)
        }

        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()

        if( recipes.isEmpty() )
            viewModel.onEvent( AllRecipesEvent.RequestLocalRecipes )
    }

    private fun handleInternetConnection(internetConnection: Boolean) {
        if( !internetConnection )
            Snackbar.make(binding.constraintLayout, R.string.no_internet_connection, Snackbar.LENGTH_LONG).show()
    }

    private fun createAdapter(): RecipeAdapter {
        val adapter = RecipeAdapter()
        adapter.setListener(this)
        return adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate( R.menu.search_recipes_menu, menu )

        val searchItem = menu.findItem(R.id.action_search_recipe)
        searchView = searchItem.actionView as SearchView

        setupSearchView( searchView )
    }

    private fun setupSearchView(searchView: SearchView?) {
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener( object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if( query?.isNotEmpty() == true && query.length >= 2 ) {
                    recipeQuery = query
                    viewModel.onEvent(AllRecipesEvent.OnSearchInputSubmit(query))
                    searchView.clearFocus()
                }

                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        } )
        searchView?.setOnQueryTextFocusChangeListener { _, hasFocus ->
            // binding.tvSearching.isVisible = hasFocus
            // binding.ivSearchingRecipe.isVisible = hasFocus
            // binding.shimmerRecyclerView.isVisible = !hasFocus
        }
    }

    override fun onRecipeClick(recipeTitle: String) {
        val action = AllRecipesFragmentDirections
            .actionAllFoodRecipesFragmentToDetailsActivity( recipeTitle )
        findNavController().navigate( action )
    }
}