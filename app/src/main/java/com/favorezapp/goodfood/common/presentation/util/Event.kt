package com.favorezapp.goodfood.common.presentation.util

data class Event<out T>( private val content: T ) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if( hasBeenHandled )
            null
        else {
            hasBeenHandled = true
            content
        }
    }
}