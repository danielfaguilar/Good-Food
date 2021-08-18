package com.favorezapp.goodfood

import android.app.Application
import com.favorezapp.logging.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GoodFoodApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
    }
    private fun initLogger() {
        Logger.init()
    }
}