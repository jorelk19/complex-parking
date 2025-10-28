package com.complexparking.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreens(val route: String) {
    @Serializable
    data object PERMISSIONSSCREEN: AppScreens("PERMISSIONS_SCREEN")
    @Serializable
    data object SPLASHSCREEN: AppScreens("SPLASH_SCREEN")
    @Serializable
    data object LOGINSCREEN: AppScreens("LOGIN_SCREEN")
    @Serializable
    data object HOMESCREEN: AppScreens("HOME_SCREEN")
    @Serializable
    data object SEARCHSCREEN: AppScreens("SEARCH_SCREEN")
    @Serializable
    data object EDITSCREEN: AppScreens("EDIT_SCREEN")
    @Serializable
    data object SETTINGSCREEN: AppScreens("SETTINGS_SCREEN")
    @Serializable
    data object WIZARDSCREEN: AppScreens("WIZARD_SCREEN")
}