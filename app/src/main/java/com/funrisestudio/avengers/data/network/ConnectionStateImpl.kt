package com.funrisestudio.avengers.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class ConnectionStateImpl(private val context: Context): ConnectionState {

    @Suppress("DEPRECATION")
    override fun isConnected(): Boolean {
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