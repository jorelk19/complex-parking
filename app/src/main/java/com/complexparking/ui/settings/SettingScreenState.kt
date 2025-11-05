package com.complexparking.ui.settings

import com.complexparking.entities.UserData
import com.complexparking.ui.navigation.AppScreens

data class SettingScreenState(
    val screen: AppScreens = AppScreens.SETTINGSCREEN,
    val userData: UserData = UserData()
)
