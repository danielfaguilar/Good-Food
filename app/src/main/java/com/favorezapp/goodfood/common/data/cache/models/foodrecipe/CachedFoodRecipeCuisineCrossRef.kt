package com.favorezapp.goodfood.common.data.cache.models.foodrecipe

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["title", "cuisine"],
    indices = [Index("cuisine")]
)
data class CachedFoodRecipeCuisineCrossRef(
    val title: String,
    val cuisine: String
)