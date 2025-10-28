package com.complexparking.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.complexparking.ui.edit.EditScreen
import com.complexparking.ui.home.HomeScreen
import com.complexparking.ui.printer.PrinterScreen
import com.complexparking.ui.search.SearchScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.HOMESCREEN.route
    ) {
        composable(route = AppScreens.HOMESCREEN.route) {
            HomeScreen(navController = navController, modifier)
        }
        composable(route = AppScreens.SEARCHSCREEN.route) {
            SearchScreen(navController = navController, modifier)
        }
        composable(route = AppScreens.EDITSCREEN.route) {
            EditScreen(navController = navController, modifier)
        }
        composable(route = AppScreens.SETTINGSCREEN.route) {
            //SettingsScreen(navController =
            PrinterScreen(modifier)
        }
    }
}

private const val LOGIN_SCREE_PARAMETER = "loginScreenModel"