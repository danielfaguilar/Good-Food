package com.favorezapp.goodfood.common.details.presentation.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.favorezapp.goodfood.common.details.presentation.DetailsActivityState
import com.favorezapp.goodfood.common.details.presentation.viewmodels.SharedViewModel
import com.favorezapp.goodfood.databinding.FragmentInstructionsBinding
import com.favorezapp.logging.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstructionsFragment: Fragment() {
    private val viewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentInstructionsBinding? = null
    val binding: FragmentInstructionsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewStateChanges()
    }

    private fun observeViewStateChanges() {
        viewModel.state.observe(viewLifecycleOwner) {
            updateState( it )
        }
    }

    private fun updateState(newState: DetailsActivityState) {
        val builder = StringBuilder()

        val instructions = newState.foodRecipe?.analyzedInstructions.orEmpty()

        for( instruction in instructions ) {
            builder.append( instruction.name + '\n')
            for( step in instruction.steps ) {
                builder.append( "${step.number} - ${step.step}\n")
            }
        }

        binding.textViewInstructions.text = builder.toString()
    }

    private fun setupUI() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}