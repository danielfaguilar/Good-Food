package com.favorezapp.goodfood.common.presentation.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.favorezapp.goodfood.R
import com.favorezapp.goodfood.allrecipes.presentation.AllRecipesFragmentDirections
import org.jsoup.Jsoup
import java.lang.Exception

@BindingAdapter("applyGreenColorIfVegan")
fun applyGreenColorIfVegan(view: View, vegan: Boolean) {
    if( vegan )
        when( view ) {
            is TextView -> view.setTextColor(
                ContextCompat
                    .getColor(view.context, R.color.green)
            )
            is ImageView -> view.setColorFilter(
                ContextCompat
                    .getColor(view.context, R.color.green)
            )
        }
}

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, imageUrl: String) =
    Glide.with(imageView.context)
        .load(imageUrl.ifEmpty { null })
        .error(R.drawable.donut)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)

@BindingAdapter("onRecipeClickListener")
fun onRecipeClickListener( itemRowRecipeLayout: ConstraintLayout, recipeTitle: String ) {
    itemRowRecipeLayout.setOnClickListener {
        try {
            val action = AllRecipesFragmentDirections
                .actionAllFoodRecipesFragmentToDetailsActivity( recipeTitle )
            itemRowRecipeLayout.findNavController().navigate(action)
        }catch (e: Exception) { e.printStackTrace() }
    }
}

@BindingAdapter("parseHtml")
fun parseHtml( tv: TextView, description: String? ) {
    if( description != null ) {
        val textParsed = Jsoup.parse(description).text()
        tv.text = textParsed
    }
}