package com.complexparking.ui.settings.menuScreens

import androidx.compose.runtime.mutableStateOf
import com.complexparking.ui.base.BaseViewModel

class ParkingSettingsScreenViewModel: BaseViewModel() {

    private val _parkingSettingsModel = mutableStateOf(ParkingSettingsModel())
    val parkingSettingsModel get() = _parkingSettingsModel

    override fun onStartScreen() {
        _parkingSettingsModel.value = ParkingSettingsModel()
    }
}