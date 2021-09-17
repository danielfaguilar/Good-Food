package com.favorezapp.goodfood.common.details.presentation.more_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.favorezapp.goodfood.common.details.presentation.DetailsActivityState
import com.favorezapp.goodfood.common.details.presentation.viewmodels.SharedViewModel
import com.favorezapp.goodfood.databinding.FragmentMoreDetailsBinding

class MoreDetailsFragment: Fragment() {
    private lateinit var _binding: FragmentMoreDetailsBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreDetailsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeActivityViewStateChanges()
    }

    private fun observeActivityViewStateChanges() {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreen( it )
        }
    }

    private fun updateScreen(state: DetailsActivityState) {
        if( state.foodRecipe == null ) return

        setupWebView( state.foodRecipe.sourceUrl )
    }

    private fun setupWebView(dataUrl: String ) {
        with( _binding.webViewInstructions ) {
            _binding.progressBar.visibility = View.VISIBLE
            webViewClient = object: WebViewClient() {}
            loadUrl( dataUrl )
            _binding.progressBar.visibility = View.GONE
        }
    }

}