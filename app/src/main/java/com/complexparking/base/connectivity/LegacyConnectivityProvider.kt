package com.complexparking.base.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.net.ConnectivityManager.EXTRA_NETWORK_INFO
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

@Suppress("DEPRECATION")
class LegacyConnectivityProvider(
    private val context: Context,
    private val cm: ConnectivityManager,
) : BaseConnectivityProvider() {

    private val receiver = ConnectivityReceiver()
    private val networkCallback = ConnectivityCallback()

    override fun subscribe() {
        context.registerReceiver(receiver, IntentFilter(CONNECTIVITY_ACTION))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback)
        }
    }

    override fun unsubscribe() {
        context.unregisterReceiver(receiver)
        cm.unregisterNetworkCallback(networkCallback)
    }

    override fun getNetworkState(): NetworkState {
        val activeNetworkInfo = cm.activeNetworkInfo
        return if (activeNetworkInfo != null) {
            NetworkState.ConnectedState.ConnectedLegacy(activeNetworkInfo)
        } else {
            NetworkState.NotConnectedState
        }
    }

    private inner class ConnectivityCallback : ConnectivityManager.NetworkCallback() {

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            dispatchChange(NetworkState.ConnectedState.Connected(capabilities))
        }

        override fun onLost(network: Network) {
            dispatchChange(NetworkState.NotConnectedState)
        }
    }

    private inner class ConnectivityReceiver : BroadcastReceiver() {
        override fun onReceive(c: Context, intent: Intent) {
            // on some devices ConnectivityManager.getActiveNetworkInfo() does not provide the correct network state
            // https://issuetracker.google.com/issues/37137911
            val networkInfo = cm.activeNetworkInfo
            val fallbackNetworkInfo: NetworkInfo? = intent.getParcelableExtra(EXTRA_NETWORK_INFO)
            // a set of dirty workarounds
            val state: NetworkState =
                if (networkInfo?.isConnectedOrConnecting == true) {
                    NetworkState.ConnectedState.ConnectedLegacy(networkInfo)
                } else if (networkInfo != null && fallbackNetworkInfo != null &&
                    networkInfo.isConnectedOrConnecting != fallbackNetworkInfo.isConnectedOrConnecting
                ) {
                    NetworkState.ConnectedState.ConnectedLegacy(
                        fallbackNetworkInfo
                    )
                } else {
                    val state = networkInfo ?: fallbackNetworkInfo
                    if (state != null) NetworkState.ConnectedState.ConnectedLegacy(
                        state
                    ) else NetworkState.NotConnectedState
                }
            dispatchChange(state)
        }
    }
}