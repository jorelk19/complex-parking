package com.complexparking.base

import android.content.Context
import com.complexparking.base.connectivity.BaseConnectivityProvider
import com.complexparking.di.KoinManager
import com.complexparking.data.repository.local.ParkingDatabase

open class ComplexParkingApplication: BaseApplication() {
    private val providerBase: BaseConnectivityProvider by lazy {
        BaseConnectivityProvider.createProvider(
            this@ComplexParkingApplication
        )
    }

    /**
     * Get the application context
     * */
    companion object {
        private lateinit var appContext: Context
        fun getAppContext(): Context = appContext
    }

    /**
     * Object to manage the internet connection
     * */

    private val connectivityStateListener =
        object : BaseConnectivityProvider.ConnectivityStateListener {
            override fun onStateChange(state: BaseConnectivityProvider.NetworkState) {
                //settingsSharedPreferences.setInternetState(state.hasInternet())
            }

            private fun BaseConnectivityProvider.NetworkState.hasInternet(): Boolean {
                return (this as? BaseConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
            }
        }

    /**
     * Method that allow start koin dependency injection
     * */
    override fun onAppStart() {
        appContext = this
        val db by lazy { ParkingDatabase.buildDatabase(appContext) }
        KoinManager.initKoin(appContext, db)
        providerBase.addListener(connectivityStateListener)
    }

    override fun onAppDestroy() {
    }
}