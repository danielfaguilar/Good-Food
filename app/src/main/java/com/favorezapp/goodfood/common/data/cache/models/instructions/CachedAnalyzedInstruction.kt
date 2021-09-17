package com.favorezapp.goodfood.common.data.cache.models.instructions

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.favorezapp.goodfood.common.domain.model.foodrecipe.AnalyzedInstruction
import com.favorezapp.goodfood.common.domain.model.foodrecipe.Step

@Entity( tableName = "analyzed_instruction" )
class CachedAnalyzedInstruction(
    @PrimaryKey( autoGenerate = false )
    val recipeTitle: String,
    val name: String,
    val steps: List<Step>
) {

    companion object {
        fun fromDomain( entity: AnalyzedInstruction ) =
            CachedAnalyzedInstruction( entity.recipeTitle.orEmpty(), entity.name, entity.steps )
    }

    fun toDomain() =
        AnalyzedInstruction(
            recipeTitle,
            name,
            steps
        )
}