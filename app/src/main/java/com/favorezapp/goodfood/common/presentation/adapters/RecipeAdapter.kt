package com.favorezapp.goodfood.common.presentation.adapters

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.favorezapp.goodfood.common.presentation.model.UIFoodRecipe
import com.favorezapp.goodfood.common.utils.RecipesDiffUtil
import com.favorezapp.goodfood.databinding.ItemRowRecipeBinding

class RecipeAdapter: RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {

    private var recipes = emptyList<UIFoodRecipe>()
    private var listener: OnRecipeClickListener ?= null

    interface OnRecipeClickListener {
        fun onRecipeClick( recipeTitle: String )
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
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
        holder.binding.cardFoodRecipe.setOnClickListener {
            listener?.onRecipeClick(currentRecipe.title)
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: List<UIFoodRecipe>) {
        val recipesDiffUtil =
            RecipesDiffUtil(recipes, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}