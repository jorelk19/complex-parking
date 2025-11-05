package com.complexparking.ui.settings.menuScreens

data class ParkingSettingsState(
    val complexName: String = "",
    val quantityUnit: String = "",
    val complexAddress: String = "",
    val parkingQuantity: String = "",
    val adminName: String = "",
    val currentParkingId: Int = 0,
    val parkingMaxHourFree: String = "",
    val parkingHourPrice: String = "",
    val isButtonEnabled: Boolean = false
)
