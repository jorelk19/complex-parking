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
import com.complexparking.ui.widgets.CustomGeneralHeader

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
                    header = {
                        CustomGeneralHeader("¡Hola! Edson Nieto")
                    },
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