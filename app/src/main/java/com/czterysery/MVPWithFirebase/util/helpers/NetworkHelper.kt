package com.czterysery.MVPWithFirebase.util.helpers

import android.content.Context
import android.net.ConnectivityManager

//Returns whether user is connected to any network (WiFi or Mobile)
class NetworkHelper(private val context: Context) {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}