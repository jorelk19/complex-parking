package com.complexparking.ui.settings

import com.complexparking.entities.UserData
import com.complexparking.ui.navigation.AppScreens

data class SettingScreenState(
    val screenTarget: AppScreens = AppScreens.NONE,
    val userData: UserData = UserData()
)
