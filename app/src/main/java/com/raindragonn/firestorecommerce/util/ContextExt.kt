package com.raindragonn.firestorecommerce.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes


// https://stackoverflow.com/a/65238372
val Context.isNetworkConnected: Boolean
    get() {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+
            connMgr.getNetworkCapabilities(connMgr.activeNetwork)?.let { networkCapabilities ->
                return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            } ?: false
        } else {
            var isWifiConn: Boolean = false
            var isMobileConn: Boolean = false

            connMgr.allNetworks.forEach { network ->
                connMgr.getNetworkInfo(network)?.apply {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        isWifiConn = isWifiConn or isConnected
                    }
                    if (type == ConnectivityManager.TYPE_MOBILE) {
                        isMobileConn = isMobileConn or isConnected
                    }
                }
            }

            return isWifiConn || isMobileConn
        }
    }

fun Context.showToast(msg: String, isLong: Boolean = true) {
    Toast.makeText(this, msg, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        .show()
}

fun Context.showToast(
    @StringRes
    msg: Int, isLong: Boolean = true
) {
    Toast.makeText(this, msg, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        .show()
}