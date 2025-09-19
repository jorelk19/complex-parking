package com.complexparking.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.complexparking.ui.navigation.AuthenticationNavigation
import com.complexparking.ui.theme.ComplexParkingTheme
import com.complexparking.ui.utilities.LoadingManager

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            ComplexParkingTheme {
                LoadingManager.showLoader()
                AuthenticationNavigation(navHostController)
            }
        }
    }
}