package com.favorezapp.goodfood.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE







class NetworkListener: ConnectivityManager.NetworkCallback() {
    private val isNetworkAvailable = MutableStateFlow( false )

    fun checkNetAvailability(context: Context): StateFlow<Boolean> {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager


        var isConnected = false

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
            connectivityManager.registerDefaultNetworkCallback(this)

            connectivityManager.allNetworks.forEach { network ->
                val netCapability = connectivityManager.getNetworkCapabilities( network )
                netCapability?.let {
                    if( it.hasCapability( NetworkCapabilities.NET_CAPABILITY_INTERNET ) ) {
                        isConnected = true
                        return@forEach
                    }
                }
            }
        } else
            isConnected = connectivityManager.activeNetworkInfo?.isConnected ?: false


        isNetworkAvailable.value = isConnected

        return isNetworkAvailable
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }
}