package com.complexparking.ui.settings.menuScreens

data class ParkingSettingsState(
    val currentParkingId: Int = 0,
    val parkingHourPrice: String = "",
    val parkingMaxFreeHour: String = "",
    val isButtonEnabled: Boolean = false
)
