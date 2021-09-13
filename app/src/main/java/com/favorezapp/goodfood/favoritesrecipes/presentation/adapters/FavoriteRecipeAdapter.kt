package com.favorezapp.goodfood.favoritesrecipes.presentation.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.favorezapp.goodfood.R
import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe
import com.favorezapp.goodfood.common.utils.RecipesDiffUtil
import com.favorezapp.goodfood.databinding.ItemRowRecipeBinding

class FavoriteRecipeAdapter(
    private val fragmentActivity: FragmentActivity
) : RecyclerView.Adapter<FavoriteRecipeAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private lateinit var actionMode: ActionMode
    private var selectedRecipes = arrayListOf<UIFoodRecipe>()
    private var recipesHolders = arrayListOf<MyViewHolder>()

    private var recipes = emptyList<UIFoodRecipe>()
    private var listener: OnRecipeClickListener ?= null

    interface OnRecipeClickListener {
        fun onRecipeClick( recipeTitle: String )
        fun onDeleteRecipesClick( recipesTitles: List<String> )
    }

    fun setListener( clickListener: OnRecipeClickListener ) {
        listener = clickListener
    }

    class MyViewHolder(val binding: ItemRowRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: UIFoodRecipe){
            binding.uiFoodRecipe = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRowRecipeBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        recipesHolders.add(holder)

        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)

        saveItemStateOnScroll( currentRecipe, holder )

        /* Click listener */
        holder.binding.cardFoodRecipe.setOnClickListener {
            if( multiSelection )
                applySelection(holder, currentRecipe)
            else
                listener?.onRecipeClick(currentRecipe.title)
        }

        /* Long click listener */
        holder.binding.cardFoodRecipe.setOnLongClickListener {
            if( !multiSelection ) {
                multiSelection = true
                fragmentActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
            }
            true
        }
    }

    private fun saveItemStateOnScroll(recipe: UIFoodRecipe, holder: MyViewHolder) {
        if( selectedRecipes.contains( recipe ) )
            changeRecipeStyle( holder, R.color.background_light_color, R.color.design_default_color_primary )
        else
            changeRecipeStyle( holder, R.color.card_background_color, R.color.stroke_color )
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    private fun applySelection(holder: MyViewHolder, currentUiRecipe: UIFoodRecipe) {
        if( selectedRecipes.contains( currentUiRecipe ) ) {
            selectedRecipes.remove( currentUiRecipe )
            changeRecipeStyle( holder, R.color.card_background_color, R.color.stroke_color )
        } else {
            selectedRecipes.add( currentUiRecipe )
            changeRecipeStyle( holder, R.color.background_light_color, R.color.design_default_color_primary )
        }
        setActionModeBarTitle()
    }

    private fun setActionModeBarTitle() {
        when( selectedRecipes.size ) {
            0 -> actionMode.finish()
            1 -> actionMode.title = "${selectedRecipes.size} item selected"
            else -> actionMode.title = "${selectedRecipes.size} items selected"
        }
    }

    private fun changeRecipeStyle( holder: MyViewHolder, backColorRes: Int, strokeColorRes: Int ) {
        holder.binding.itemRowConstraintLayout.setBackgroundColor(
            ContextCompat.getColor(fragmentActivity, backColorRes)
        )
        holder.binding.cardFoodRecipe.strokeColor =
            ContextCompat.getColor( fragmentActivity, strokeColorRes )
    }

    fun setData(newData: List<UIFoodRecipe>) {
        val recipesDiffUtil =
            RecipesDiffUtil(recipes, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun applyStatusBarColor( colorRef: Int ) {
        fragmentActivity.window.statusBarColor =
            ContextCompat.getColor(fragmentActivity, colorRef)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate( R.menu.favorite_recipes_contextual_menu, menu )
        applyStatusBarColor(R.color.status_bar_color_contextual)
        actionMode = mode!!
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if( item?.itemId == R.id.ic_action_delete_fav_recipe )
            listener?.onDeleteRecipesClick(
                selectedRecipes.map { it.title }
            )
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        for( holder in recipesHolders )
            changeRecipeStyle(holder, R.color.card_background_color, R.color.stroke_color)

        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.status_bar_color)
    }

    fun closeActionMode() {
        if( this::actionMode.isInitialized )
            actionMode.finish()
    }
}