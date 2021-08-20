package com.favorezapp.goodfood.allrecipes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.favorezapp.goodfood.R
import com.favorezapp.goodfood.common.presentation.RecipeAdapter
import com.favorezapp.goodfood.databinding.FragmentAllFoodRecipesBinding

class AllFoodRecipesFragment : Fragment(R.layout.fragment_all_food_recipes) {
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
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
    }

    private fun setupRecyclerView(recipeAdapter: RecipeAdapter) {
        binding.shimmerRecyclerView.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun createAdapter() = RecipeAdapter()
}