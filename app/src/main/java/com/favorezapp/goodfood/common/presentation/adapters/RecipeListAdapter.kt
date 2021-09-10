package com.favorezapp.goodfood.common.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe
import com.favorezapp.goodfood.databinding.ItemRowRecipeBinding

val DIFF_UTIL = object: DiffUtil.ItemCallback<UIFoodRecipe>() {
    override fun areItemsTheSame(oldItem: UIFoodRecipe, newItem: UIFoodRecipe): Boolean {
        return oldItem.title == newItem.title
    }
    override fun areContentsTheSame(oldItem: UIFoodRecipe, newItem: UIFoodRecipe): Boolean {
        return oldItem == newItem
    }
}

class RecipeListAdapter: ListAdapter<UIFoodRecipe, RecipeListAdapter.RecipeViewHolder>(DIFF_UTIL) {
    inner class RecipeViewHolder(
        private val binding: ItemRowRecipeBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind (uiFoodRecipe: UIFoodRecipe){
            binding.uiFoodRecipe = uiFoodRecipe
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemRowRecipeBinding
            .inflate( inflater, parent,false )

        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind( getItem(position) )
    }
}