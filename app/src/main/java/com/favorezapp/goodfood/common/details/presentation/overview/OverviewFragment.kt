package com.favorezapp.goodfood.common.details.presentation.overview

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.favorezapp.goodfood.R
import com.favorezapp.goodfood.common.details.presentation.DetailsActivityState
import com.favorezapp.goodfood.common.details.presentation.viewmodels.SharedViewModel
import com.favorezapp.goodfood.databinding.FragmentOverviewBinding
import com.google.android.material.snackbar.Snackbar
import org.jsoup.Jsoup
import java.util.*

class OverviewFragment: Fragment() {
    private lateinit var _binding: FragmentOverviewBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeActivityViewStateChanges()
        setupListeners()
        textToSpeech = TextToSpeech(requireContext()) {
            if( it == TextToSpeech.SUCCESS )
                textToSpeech.language = Locale.ENGLISH
        }
    }

    private fun setupListeners() {
        _binding.ivSpeakOverview.setOnClickListener {
            if( !viewModel.speaking ) {
                Snackbar.make(_binding.scrollView, R.string.speaking, Snackbar.LENGTH_SHORT).show()

                (it as ImageView).setColorFilter( ContextCompat.getColor(requireContext(), R.color.green) )
                textToSpeech.speak(
                    _binding.textViewSummary.text,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                );
            }

            else if( textToSpeech.isSpeaking ) {
                (it as ImageView).setColorFilter( ContextCompat.getColor(requireContext(), R.color.white) )
                textToSpeech.stop()
            }

            viewModel.speaking = !viewModel.speaking
        }
    }

    private fun observeActivityViewStateChanges() {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreen(it)
        }
    }

    private fun updateScreen(state: DetailsActivityState) {
        if( state.foodRecipe == null )
            return

        val uiRecipe = state.foodRecipe

        with( _binding ) {
            tvTitle.text = uiRecipe.title

            Glide.with(requireContext())
                .load(uiRecipe.image)
                .into(imageViewRecipe)

            if( uiRecipe.vegan )
                setGreenColor( tvVegan, ivVegan )

            if( uiRecipe.vegetarian )
                setGreenColor( tvVegetarian, ivVegetarian )

            if( uiRecipe.glutenFree )
                setGreenColor( tvGlutenFree, ivGlutenFree )

            if( uiRecipe.dairyFree )
                setGreenColor( tvDairyFree, ivDairyFree )

            if( uiRecipe.healthy )
                setGreenColor( tvHealthy, ivHealthy )

            if( uiRecipe.cheap )
                setGreenColor( tvCheap, ivCheap )


            textViewOverviewLikes.text = uiRecipe.likes.toString()
            textViewOverviewReadyInMinutes.text = uiRecipe.readyInMinutes.toString()

            val parsedSummary = Jsoup.parse( uiRecipe.summary ).text()
            textViewSummary.text = parsedSummary
        }
    }

    private fun setGreenColor(tv: TextView, iv: ImageView) {
        tv.setTextColor( ContextCompat.getColor(requireContext(), R.color.green) )
        iv.setColorFilter( ContextCompat.getColor(requireContext(), R.color.green) )
    }

    override fun onDestroy() {
        super.onDestroy()
        if( textToSpeech.isSpeaking )
            textToSpeech.stop()
    }

}