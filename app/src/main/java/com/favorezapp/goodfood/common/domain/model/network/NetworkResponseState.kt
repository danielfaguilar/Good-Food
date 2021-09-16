package com.favorezapp.goodfood.common.domain.model.network

sealed class NetworkResponseState<T> {

    data class Success<T>( val result: T ): NetworkResponseState<T>()

    class InvalidData<T>: NetworkResponseState<T>() {
        companion object { const val CODE: Int = 0 }
    }
    data class Error<T>( val message: String ): NetworkResponseState<T>() {
        companion object { const val CODE: Int = 1 }
    }
    data class NetworkException<T>(val message: String): NetworkResponseState<T>() {
        companion object { const val CODE: Int = 2 }
    }

    sealed class HttpError<T>: NetworkResponseState<T>() {
        data class ResourceForbidden<T>(val message: String) : HttpError<T>() {
            companion object { const val CODE: Int = 403 }
        }
        data class ResourceNotFound<T>(val message: String) : HttpError<T>() {
            companion object { const val CODE: Int = 404 }
        }
        data class InternalServerError<T>(val message: String) : HttpError<T>() {
            companion object { const val CODE: Int = 500 }
        }
        data class BadGateWay<T>(val message: String) : HttpError<T>() {
            companion object { const val CODE: Int = 502 }
        }
        data class ResourceRemoved<T>(val message: String) : HttpError<T>() {
            companion object { const val CODE: Int = 301 }
        }
        data class RemovedResourceFound<T>(val message: String) : HttpError<T>() {
            companion object { const val CODE: Int = 302 }
        }
        data class InvalidCredentials<T>(val message: String) : HttpError<T>() {
            companion object { const val CODE: Int = 401 }
        }
    }

}