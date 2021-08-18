package com.favorezapp.goodfood.common.domain.model

import java.io.IOException

class NetworkException(message: String): Exception(message)

class NetworkUnavailableException(message: String = "No network available :c"): IOException(message)