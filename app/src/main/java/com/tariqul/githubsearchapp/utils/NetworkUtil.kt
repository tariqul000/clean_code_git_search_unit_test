package com.tariqul.githubsearchapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class NetworkUtil @Inject constructor(
    private val context: Context
) {

    /**
     * Check if the device is connected to the internet.
     * This works for API level >= 23 (Marshmallow).
     */
    @Suppress("DEPRECATION")
    fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            // Fallback for devices below Marshmallow
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    /**
     * Monitor network changes in real-time using LiveData.
     */
    fun observeNetworkState(): LiveData<Boolean> {
        val networkLiveData = MutableLiveData<Boolean>()
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    networkLiveData.postValue(true)
                }

                override fun onLost(network: Network) {
                    networkLiveData.postValue(false)
                }
            })
        } else {
            // For devices below Nougat
            val activeNetwork = connectivityManager.activeNetworkInfo
            networkLiveData.postValue(activeNetwork != null && activeNetwork.isConnected)
        }

        return networkLiveData
    }
}
