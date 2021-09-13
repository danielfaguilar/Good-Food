package com.favorezapp.goodfood.common.details.presentation.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.favorezapp.goodfood.common.details.presentation.DetailsActivityState
import com.favorezapp.goodfood.common.details.presentation.viewmodels.SharedViewModel
import com.favorezapp.goodfood.common.presentation.adapters.ExtIngredientAdapter
import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe
import com.favorezapp.goodfood.databinding.FragmentIngredientsBinding

class IngredientsFragment: Fragment() {
    private lateinit var _binding: FragmentIngredientsBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val ingredientsAdapter: ExtIngredientAdapter = ExtIngredientAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeActivityViewStateChanges()
    }

    private fun observeActivityViewStateChanges() {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState( it )
        }
    }

    private fun updateScreenState(newState: DetailsActivityState?) {
        handleFoodRecipe( newState?.foodRecipe )
    }

    private fun handleFoodRecipe(foodRecipe: UIFoodRecipe?) {
        if( foodRecipe == null ) return
        val ingredients = foodRecipe.extendedIngredients
        ingredientsAdapter.setData( ingredients )
    }

    private fun setupUI() {
        _binding.recyclerViewIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}