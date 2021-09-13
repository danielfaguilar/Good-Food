package com.favorezapp.goodfood.foodjoke.data.api.model.mappers

import com.favorezapp.goodfood.foodjoke.data.api.model.ApiFoodJoke
import com.favorezapp.goodfood.foodjoke.domain.model.FoodJoke
import javax.inject.Inject

class ApiFoodJokeMapper @Inject constructor(): ApiMapper<ApiFoodJoke, FoodJoke> {
    override fun mapTo(api: ApiFoodJoke): FoodJoke {
        return FoodJoke( api.text )
    }
}