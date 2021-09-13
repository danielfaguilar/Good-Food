package com.favorezapp.goodfood.foodjoke.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.favorezapp.goodfood.foodjoke.domain.model.FoodJoke

@Entity(
    tableName = "food_joke_table"
)
class CachedFoodJoke(
    @PrimaryKey( autoGenerate = false )
    val joke: String
) {
    companion object {
        fun fromDomain(joke: FoodJoke) =
            CachedFoodJoke( joke.joke )
    }

    fun toDomain() = FoodJoke( joke )
}