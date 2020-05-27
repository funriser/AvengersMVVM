package com.funrisestudio.avengers.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkHandler(private val context: Context) {

    @Suppress("DEPRECATION")
    fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        cm?:return false
        return if (Build.VERSION.SDK_INT < 23) {
            val networkInfo = cm.activeNetworkInfo
            networkInfo?.isConnectedOrConnecting == true
        } else {
            val nc = cm.getNetworkCapabilities(cm.activeNetwork)
            nc?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
                    || nc?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
                    || nc?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true
                    || nc?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true
        }
    }

}