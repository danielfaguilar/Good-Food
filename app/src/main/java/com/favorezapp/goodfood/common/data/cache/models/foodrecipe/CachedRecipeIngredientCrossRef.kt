package com.favorezapp.goodfood.common.data.cache.models.foodrecipe

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["title", "name"],
    indices = [Index("name")]
)
data class CachedRecipeIngredientCrossRef(
    val title: String,
    val name: String
)