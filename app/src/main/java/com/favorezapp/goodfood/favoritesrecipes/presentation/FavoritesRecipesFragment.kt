package com.favorezapp.goodfood.favoritesrecipes.presentation

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.favorezapp.goodfood.R
import com.favorezapp.goodfood.common.presentation.util.Event
import com.favorezapp.goodfood.databinding.FragmentFavoritesRecipesBinding
import com.favorezapp.goodfood.favoritesrecipes.presentation.adapters.FavoriteRecipeAdapter
import dagger.hilt.android.AndroidEntryPoint

const val DELETE_ALL_FAVORITES_DIALOG = "delete_all_favorites"

@AndroidEntryPoint
class FavoritesRecipesFragment : Fragment(),
    FavoriteRecipeAdapter.OnRecipeClickListener, DeleteAllDialog.Listener {
    private val viewModel: FavoriteRecipesViewModel by viewModels()
    private var _binding: FragmentFavoritesRecipesBinding ?= null
    private val binding: FragmentFavoritesRecipesBinding get() = _binding!!
    private lateinit var mRecipeAdapter: FavoriteRecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupUI()

        if( savedInstanceState != null ) {
            val dialog = parentFragmentManager.findFragmentByTag(
                DELETE_ALL_FAVORITES_DIALOG)
                    as DeleteAllDialog
            dialog.setListener( this )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate( R.menu.favorite_recipes_menu, menu )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if( item.itemId == R.id.ic_action_delete_all_fav_recipes )
            showDeleteAllDialog()
        else
            return super.onOptionsItemSelected(item)

        return true
    }

    private fun showDeleteAllDialog() {
        DeleteAllDialog()
            .setListener(this)
            .show(parentFragmentManager, DELETE_ALL_FAVORITES_DIALOG)
    }

    private fun setupUI() {
        mRecipeAdapter = createAdapter()
        setupRecyclerView( mRecipeAdapter )
        observeViewStateChanges( mRecipeAdapter )
    }

    private fun observeViewStateChanges(adapter: FavoriteRecipeAdapter ) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreen( it, adapter )
        }
    }

    private fun updateScreen(
        newState: FavoriteRecipesViewState,
        adapter: FavoriteRecipeAdapter
    ) {

        handleNoFavoriteRecipes(
            newState.favRecipes.isEmpty()
        )

        handleFailure( newState.failure )
        adapter.setData( newState.favRecipes )
    }

    private fun handleNoFavoriteRecipes(noRecipes: Boolean) {
        binding.tvNoRecipes.isVisible = noRecipes
        binding.imageView.isVisible = noRecipes
    }

    private fun handleFailure(failure: Event<Throwable>?) {
        val unhandledException = failure?.getContentIfNotHandled() ?: return
    }

    private fun setupRecyclerView(recipesAdapter: FavoriteRecipeAdapter) {
        binding.recyclerViewFavRecipes.apply {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun createAdapter(): FavoriteRecipeAdapter {
        val adapter = FavoriteRecipeAdapter( requireActivity() )
        adapter.setListener(this)
        return adapter
    }

    override fun onRecipeClick(title: String) {
        val action = FavoritesRecipesFragmentDirections
            .actionFavoritesFoodRecipesFragmentToDetailsActivity(title)
        findNavController().navigate(action)
    }

    override fun onDeleteRecipesClick(recipesTitles: List<String>) {
        mRecipeAdapter.closeActionMode()

        for (recipeId in recipesTitles)
            viewModel.onEvent(
                FavoriteRecipesEvent
                    .DeleteFavoriteRecipeById(recipeId)
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        mRecipeAdapter.closeActionMode()
        binding.recyclerViewFavRecipes.adapter = null
        _binding = null
    }

    override fun deleteAllFavorites() {
        viewModel.onEvent(FavoriteRecipesEvent.DeleteFavoriteRecipes)
    }
}