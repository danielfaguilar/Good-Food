package com.favorezapp.goodfood.common.data.cache.models.foodrecipe

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["foodRecipeId", "cuisine"],
    indices = [Index("cuisine")]
)
data class CachedFoodRecipeCuisineCrossRef(
    val foodRecipeId: Long,
    val cuisine: String
)