package com.complexparking.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleObserver

/**
 * Abstract class that contain the application instance and lifeCycle observer
 * @author Edson Joel Nieto Ardila
 * @since 1.0.0
 * */
abstract class BaseApplication : Application(), LifecycleObserver {
    abstract fun onAppStart()

    abstract fun onAppDestroy()

    abstract fun onAppTerminate()

    /**
     * Method that allow capture when the application start
     * */
    override fun onCreate() {
        super.onCreate()
        onAppStart()
    }

    override fun onTerminate() {
        super.onTerminate()
        onAppTerminate()
    }

    companion object {
        // Debounce 5 secs
        private const val DEFAULT_TIME = 5000L
        private const val APP_SYNC_TIME = 60000L
        private lateinit var appContext: Context

        fun getAppContext(): Context = appContext
    }
}