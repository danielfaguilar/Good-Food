package com.favorezapp.goodfood.common.details.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.favorezapp.goodfood.R
import com.favorezapp.goodfood.common.details.presentation.adapters.PagerAdapter
import com.favorezapp.goodfood.common.details.presentation.ingredients.IngredientsFragment
import com.favorezapp.goodfood.common.details.presentation.instructions.MoreDetailsFragment
import com.favorezapp.goodfood.common.details.presentation.overview.OverviewFragment
import com.favorezapp.goodfood.common.details.presentation.viewmodels.SharedViewModel
import com.favorezapp.goodfood.databinding.ActivityDetailsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: SharedViewModel by viewModels()
    private val safeArgs by navArgs<DetailsActivityArgs>()
    private var favMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate( layoutInflater )
        setContentView(binding.root)
        setupUI()
        getFoodRecipe( safeArgs.recipeTitle )
    }

    private fun setupUI() {
        setSupportActionBar( binding.toolbar )
        supportActionBar?.setDisplayHomeAsUpEnabled( true )
        setupPagerAdapterAndTabLayout()
    }

    private fun setupPagerAdapterAndTabLayout() {
        val fragmentList = createFragmentList()
        val titleList = createTitleList()
        val dataBundle = Bundle()

        val pagerAdapter = PagerAdapter(
            dataBundle,
            fragmentList,
            this
        )

        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator( binding.tabLayout, binding.viewPager2 ) { tab, position ->
            tab.text = titleList[ position ]
        }.attach()
    }

    private fun createTitleList(): List<String> {
        return resources.getStringArray(R.array.view_pager_fragment_titles).toList()
    }

    private fun createFragmentList(): List<Fragment> {
        return listOf(
            OverviewFragment(),
            IngredientsFragment(),
            MoreDetailsFragment()
        )
    }

    private fun getFoodRecipe(title: String) {
        viewModel.onEvent( DetailsActivityEvent.GetFoodRecipe(title) )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_add_to_favorites -> addRecipeToFavorites( item )
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun addRecipeToFavorites(item: MenuItem) {
        if( !viewModel.favorite )
            Snackbar.make(binding.constraintLayout, R.string.added_to_favorites, Snackbar.LENGTH_SHORT).show()

        if( viewModel.favorite ) {
            updateFavoriteIcon(item, false)
            viewModel.favorite = false
            viewModel.onEvent( DetailsActivityEvent.RemoveFavoriteRecipe )
        } else {
            updateFavoriteIcon(item, true)
            viewModel.favorite = true
            viewModel.onEvent( DetailsActivityEvent.SaveFavoriteRecipe )
        }
    }

    private fun updateFavoriteIcon( item: MenuItem, favorite: Boolean ) {
        setTintToItem( item, if( favorite ) R.color.yellow else R.color.white )
    }

    private fun setTintToItem(item: MenuItem, color: Int) {
        item.icon.setTint(
            ContextCompat.getColor(
                this,
                color
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_recipe_menu, menu)
        favMenuItem = menu?.findItem(R.id.action_add_to_favorites)!!
        updateFavoriteIcon(favMenuItem!!, viewModel.favorite)
        return true
    }
}