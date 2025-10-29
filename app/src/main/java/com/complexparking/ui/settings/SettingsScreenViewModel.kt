package com.complexparking.ui.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.CloseSessionUseCase
import com.complexparking.ui.base.BaseViewModel
import com.complexparking.ui.navigation.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val closeSessionUseCase: CloseSessionUseCase
): BaseViewModel() {

    private val _settingScreenState = MutableStateFlow(SettingScreenState())
    val settingScreenState get() = _settingScreenState.asStateFlow()
    private val _menuItemSelected = mutableStateOf(SettingsMenuItem.NONE)
    override fun onStartScreen() {
        _settingScreenState.value = SettingScreenState()
    }

    fun onItemSelected(settingsMenuItem: SettingsMenuItem) {
        _menuItemSelected.value = settingsMenuItem
        validateTargetScreen(settingsMenuItem)
    }

    private fun validateTargetScreen(settingsMenuItem: SettingsMenuItem) {
        when(settingsMenuItem) {
            SettingsMenuItem.PRINTER_ITEM -> {
                _settingScreenState.update { it.copy(screenTarget = AppScreens.SELECTPRINTERSCREEN) }
            }
            SettingsMenuItem.PARAMETERS_PARKING_ITEM -> {
                _settingScreenState.update { it.copy(screenTarget = AppScreens.PARKINGSETTINGSSCREEN) }
            }
            SettingsMenuItem.CREATE_USER_ITEM -> {
                _settingScreenState.update { it.copy(screenTarget = AppScreens.CREATEUSERSCREEN) }
            }
            SettingsMenuItem.CLOSE_SESSION_ITEM -> {
                closeSession()
            }
            SettingsMenuItem.NONE -> {}
        }
    }

    private fun closeSession() {
        viewModelScope.launch {
            closeSessionUseCase.execute().collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    result?.let {
                        if (result) {
                            _settingScreenState.update { it.copy(screenTarget = AppScreens.LOGINSCREEN) }
                        }
                    }
                }
            }
        }
    }
}