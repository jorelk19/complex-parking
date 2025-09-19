package com.complexparking.base.connectivity

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities

class ConnectivityProvider(private val cm: ConnectivityManager) :
    BaseConnectivityProvider() {

    private val networkCallback = ConnectivityCallback()

    override fun subscribe() {
        cm.registerDefaultNetworkCallback(networkCallback)
    }

    override fun unsubscribe() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    override fun getNetworkState(): NetworkState {
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return if (capabilities != null) {
            NetworkState.ConnectedState.Connected(capabilities)
        } else {
            NetworkState.NotConnectedState
        }
    }

    private inner class ConnectivityCallback : NetworkCallback() {

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            dispatchChange(NetworkState.ConnectedState.Connected(capabilities))
        }

        override fun onLost(network: Network) {
            dispatchChange(NetworkState.NotConnectedState)
        }
    }
}