package vn.md18.fsquareapplication.utils.extensions

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class OHNetworkMonitor @Inject constructor(private val context: Context) : NetworkMonitor {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isConnected(): Boolean {
        val network = connectivityManager.activeNetwork
        return network != null
    }
}

