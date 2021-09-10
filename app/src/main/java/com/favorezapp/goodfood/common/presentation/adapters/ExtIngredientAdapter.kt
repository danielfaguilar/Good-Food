package com.favorezapp.goodfood.common.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.favorezapp.goodfood.common.data.api.ApiConstants
import com.favorezapp.goodfood.common.presentation.model.UiExtendedIngredient
import com.favorezapp.goodfood.common.utils.RecipesDiffUtil
import com.favorezapp.goodfood.databinding.ItemRowIngredientBinding

class ExtIngredientAdapter: RecyclerView.Adapter<ExtIngredientAdapter.ExtIngViewHolder>() {
    private var extendedIngredientsList = emptyList<UiExtendedIngredient>()

    inner class ExtIngViewHolder(private val binding: ItemRowIngredientBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {
        fun bind( uiExtIng: UiExtendedIngredient ) {
            with( binding ) {
                tvIngredientAmount.text = uiExtIng.amount.toString()
                tvIngredientConsistency.text = uiExtIng.consistency
                tvIngredientName.text = uiExtIng.name
                tvIngredientOriginal.text = uiExtIng.original
                tvIngredientUnit.text = uiExtIng.unit

                Glide.with(context)
                    .load(ApiConstants.EXTENDED_INGREDIENT_ENDPOINT + uiExtIng.image)
                    .into( imageViewIngredient )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtIngViewHolder {
        val binding = ItemRowIngredientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExtIngViewHolder( binding, parent.context )
    }

    override fun onBindViewHolder(holder: ExtIngViewHolder, position: Int) {
        holder.bind(extendedIngredientsList[position])
    }

    override fun getItemCount(): Int {
        return extendedIngredientsList.size
    }

    fun setData( newData: List<UiExtendedIngredient> ) {
        val recipesDiffUtil = RecipesDiffUtil<UiExtendedIngredient>(
            extendedIngredientsList,
            newData
        )
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        extendedIngredientsList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}