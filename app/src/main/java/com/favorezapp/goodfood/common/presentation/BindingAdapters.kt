package com.favorezapp.goodfood.common.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.favorezapp.goodfood.R

@BindingAdapter("setNumberOfLikes")
fun setNumberOfLikes(view: TextView, likes: Int) {
    view.text = likes.toString()
}

@BindingAdapter("setReadyMinutes")
fun setReadyMinutes(view: TextView, readyInMinutes: Int) {
    view.text = readyInMinutes.toString()
}

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