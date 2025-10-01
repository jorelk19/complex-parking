package com.complexparking.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.complexparking.ui.login.LoginScreen
import com.complexparking.ui.splash.SplashScreen
import com.complexparking.ui.utilities.LoadingManager
import com.complexparking.ui.wizard.WizardScreen

@Composable
fun AuthenticationNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.SPLASHSCREEN.route
    ) {
        composable(route = AppScreens.SPLASHSCREEN.route) {
            SplashScreen(navController)
        }
        composable(route = AppScreens.LOGINSCREEN.route) {
            LoadingManager.hideLoader()
            LoginScreen(navController = navController)
        }
        composable(route = AppScreens.WIZARDSCREEN.route) {
            LoadingManager.hideLoader()
            WizardScreen(navController = navController)
        }
    }
}