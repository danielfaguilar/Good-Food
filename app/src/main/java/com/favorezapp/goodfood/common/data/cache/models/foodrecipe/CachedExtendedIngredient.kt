package com.favorezapp.goodfood.common.data.cache.models.foodrecipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.favorezapp.goodfood.common.domain.model.foodrecipe.ExtendedIngredient

@Entity(tableName = "extended_ingredient_table" )
data class CachedExtendedIngredient(
    val ingredientId: Long?,
    val amount: Double,
    val consistency: String,
    val image: String,
    @PrimaryKey( autoGenerate = false )
    val name: String,
    val original: String,
    val unit: String
) {
    companion object {
        fun fromDomain( ei: ExtendedIngredient ): CachedExtendedIngredient {
            return CachedExtendedIngredient(
                ei.id,
                ei.amount,
                ei.consistency,
                ei.image,
                ei.name,
                ei.original,
                ei.unit
            )
        }
    }
}