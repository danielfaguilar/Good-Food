package com.favorezapp.goodfood.common.data.cache.models.foodrecipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "cuisine_table" )
data class CachedCuisine(
    @PrimaryKey( autoGenerate = false )
    val cuisine: String
)