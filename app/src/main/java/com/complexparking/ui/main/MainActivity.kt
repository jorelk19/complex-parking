package com.complexparking.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.complexparking.ui.controls.SnackBarControl
import com.complexparking.ui.navigation.AppNavigation
import com.complexparking.ui.navigation.bottomNavigationBar.BottomBarControl
import com.complexparking.ui.theme.ComplexParkingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            ComplexParkingTheme {
                Scaffold(
                    snackbarHost = {
                        SnackBarControl(snackbarHostState)
                    },
                    content = { AppNavigation(Modifier.padding(it), navHostController) },
                    bottomBar = { BottomBarControl(navHostController) }
                )
            }
        }
    }
}