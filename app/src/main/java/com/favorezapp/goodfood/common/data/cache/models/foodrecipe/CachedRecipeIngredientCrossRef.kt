package com.favorezapp.goodfood.common.data.cache.models.foodrecipe

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["foodRecipeId", "ingredientId"],
    indices = [Index("ingredientId")]
)
class CachedRecipeIngredientCrossRef(
    val foodRecipeId: Long,
    val ingredientId: Long
)