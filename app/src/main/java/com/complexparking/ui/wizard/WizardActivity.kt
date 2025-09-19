package com.complexparking.ui.wizard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.complexparking.ui.theme.ComplexParkingTheme
import com.complexparking.ui.utilities.LoadingManager

class WizardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            ComplexParkingTheme {
                LoadingManager.showLoader()
                WizardScreen(navHostController)
            }
        }
    }
}