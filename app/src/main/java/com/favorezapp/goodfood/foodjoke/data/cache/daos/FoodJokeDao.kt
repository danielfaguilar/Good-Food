package com.favorezapp.goodfood.foodjoke.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.favorezapp.goodfood.foodjoke.data.cache.model.CachedFoodJoke
import io.reactivex.Flowable

@Dao
interface FoodJokeDao {
    @Query("SELECT * FROM food_joke_table")
    fun getFoodJoke(): Flowable<CachedFoodJoke>
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertFoodJoke( joke: CachedFoodJoke )
    @Query("DELETE FROM food_joke_table")
    suspend fun deleteFoodJoke()
}