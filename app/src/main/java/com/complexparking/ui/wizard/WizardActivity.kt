package com.complexparking.ui.wizard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.complexparking.ui.theme.ComplexParkingTheme

class WizardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            ComplexParkingTheme {
                val navHostController = rememberNavController()
                WizardScreen(navHostController)
            }
        }
    }
}