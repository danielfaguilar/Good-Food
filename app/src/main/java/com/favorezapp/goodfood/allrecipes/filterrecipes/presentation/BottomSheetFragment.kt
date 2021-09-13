package com.favorezapp.goodfood.allrecipes.filterrecipes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.favorezapp.goodfood.databinding.FragmentDialogBottomSheetBinding
import com.favorezapp.goodfood.allrecipes.filterrecipes.domain.model.MealAndDietType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class BottomSheetFragment: BottomSheetDialogFragment() {
    private val viewModel: BottomSheetFragmentViewModel by viewModels()

    private lateinit var _binding: FragmentDialogBottomSheetBinding
    private val binding: FragmentDialogBottomSheetBinding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogBottomSheetBinding.inflate( layoutInflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewStateUpdates()
        setupView()
        setupListeners()
    }

    private fun setupListeners() {
        binding.chipGroupMealType.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedText = chip.tag.toString().uppercase()
            viewModel.onEvent( BottomSheetFragmentEvent.MealTypeSelected(checkedId, selectedText) )
        }
        binding.chipGroupDietType.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedText = chip.tag.toString().uppercase()
            viewModel.onEvent( BottomSheetFragmentEvent.DietTypeSelected(checkedId, selectedText) )
        }

        binding.button.setOnClickListener {
            viewModel.onEvent( BottomSheetFragmentEvent.SaveMealAndDietType )
            navigateBackToRecipesFragment()
        }
    }

    private fun navigateBackToRecipesFragment() {
        val action = BottomSheetFragmentDirections
            .actionBottomSheetFragmentToAllFoodRecipesFragment(true)

        findNavController().navigate( action )
    }

    private fun setupView() {
        viewModel.onEvent( BottomSheetFragmentEvent.PrepareForChoose )
    }

    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState( it )
        }
    }

    private fun updateScreenState( newState: BottomSheetFragmentViewState ) {
        updateMealAndDietTypeChips( newState.mealAndDietType )
    }

    private fun updateMealAndDietTypeChips(mealAndDietType: MealAndDietType) {
        val mealResId = mealAndDietType.mealTypeId
        val dietResId = mealAndDietType.dietTypeId

        try {
            if( mealResId != 0 )
                updateChip( mealResId, binding.chipGroupMealType )

            if( dietResId != 0 )
                updateChip( dietResId, binding.chipGroupDietType )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateChip( chipId: Int, chipGroup: ChipGroup ) {
        if( chipId != 0 ) {
            try {
                val chip = chipGroup.findViewById<Chip>(chipId)
                chip.isChecked = true
                chipGroup.requestChildFocus( chip, chip )
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}