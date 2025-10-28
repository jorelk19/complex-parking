package com.complexparking.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.complexparking.ui.base.ContainerWithoutScroll
import com.complexparking.ui.navigation.AppNavigation
import com.complexparking.ui.navigation.bottomNavigationBar.BottomBarControl
import com.complexparking.ui.theme.ComplexParkingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            ComplexParkingTheme {
                val navHostController = rememberNavController()
                /*Scaffold(
                    bottomBar = {
                        BottomBarControl(navHostController)
                    }
                ) {
                    AppNavigation(navController = navHostController, modifier = Modifier.padding(it))
                }*/
                ContainerWithoutScroll(
                    footer = {
                        BottomBarControl(navHostController)
                    },
                    body = {
                        AppNavigation(navController = navHostController)
                    }
                )
            }
        }
    }
}