package com.favorezapp.goodfood.foodjoke.presentation

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.favorezapp.goodfood.R
import com.favorezapp.goodfood.common.presentation.util.Event
import com.favorezapp.goodfood.databinding.FragmentFoodJokeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodJokeFragment: Fragment() {
    private val viewModel: FoodJokeViewModel by viewModels()
    private var _binding: FragmentFoodJokeBinding? = null
    private val binding: FragmentFoodJokeBinding get() = _binding!!
    private var foodJoke: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupUI()
        observeJokeChanges()
        requestFoodJokeIfNeeded()
    }

    private fun setupUI() {

    }

    private fun requestFoodJokeIfNeeded() {
        if( viewModel.foodJokeStr.isEmpty() )
            viewModel.onEvent( FoodJokeEvent.RequestFoodJoke )
    }

    private fun observeJokeChanges() {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreen(it)
        }
    }

    private fun updateScreen(newState: FoodJokeViewState) {
        binding.cardViewFoodJoke.isVisible = !newState.loading
        handleLoading( newState.loading )
        handleFoodJoke( newState.joke )
        handleFailure( newState.failure )
        handleFailureMessage(newState.failureMessage)
    }

    private fun handleFailureMessage(failure: Event<String>?){
        val unhandledMessage = failure?.getContentIfNotHandled() ?: return

        Snackbar.make(binding.constraintLayoutJoke, unhandledMessage, Snackbar.LENGTH_LONG).show()

        if( foodJoke.isEmpty() )
            binding.cardViewFoodJoke.isVisible = false
    }

    private fun handleFailure(failure: Event<Throwable>?) {
        val unhandled = failure?.getContentIfNotHandled() ?: return
        binding.imageViewNoJoke.isVisible = true
        binding.textViewNoJoke.isVisible = true

        if( foodJoke.isEmpty() )
            binding.cardViewFoodJoke.isVisible = false
    }

    private fun handleFoodJoke(joke: String) {
        foodJoke = joke
        binding.textViewFoodJoke.text = joke
    }

    private fun handleLoading(loading: Boolean) {
        binding.progressBarJoke.isVisible = loading
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if( item.itemId == R.id.ic_action_share_joke ) {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, foodJoke)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
            true
        } else
            super.onOptionsItemSelected(item)
    }
}