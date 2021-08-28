package com.favorezapp.goodfood.allrecipes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.favorezapp.goodfood.R
import com.favorezapp.goodfood.common.presentation.Event
import com.favorezapp.goodfood.common.presentation.RecipeAdapter
import com.favorezapp.goodfood.databinding.FragmentAllFoodRecipesBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFoodRecipesFragment : Fragment(R.layout.fragment_all_food_recipes) {
    private val viewModel: AllFoodRecipesFragmentViewModel by viewModels()

    private lateinit var _binding: FragmentAllFoodRecipesBinding
    private val binding: FragmentAllFoodRecipesBinding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllFoodRecipesBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestInitialRecipes()
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    private fun requestInitialRecipes() {
        viewModel.onEvent(AllFoodRecipesEvent.RequestInitialRecipes)
    }

    private fun setupRecyclerView(recipeAdapter: RecipeAdapter) {
        binding.shimmerRecyclerView.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeViewStateUpdates(adapter: RecipeAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it, adapter)
        }
    }

    private fun updateScreenState(
        state: AllFoodRecipesFragmentViewState,
        adapter: RecipeAdapter
    ) {
        with( binding.shimmerRecyclerView ) {
            if(state.loading) showShimmer()
            else hideShimmer()
        }

        adapter.submitList(state.recipes)
        handleNoMoreRecipes( state.noMoreRecipes )
        handleFailures( state.failure )
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)

        val snackBarMessage =
            if (unhandledFailure.message.isNullOrEmpty())
                fallbackMessage
            else unhandledFailure.message!!

        if (snackBarMessage.isNotEmpty())
            Snackbar.make(requireView(), snackBarMessage, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleNoMoreRecipes(noMoreRecipes: Boolean) {
        // Show something here
    }

    private fun createAdapter() = RecipeAdapter()
}