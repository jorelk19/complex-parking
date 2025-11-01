package com.complexparking.ui.settings.menuScreens

import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.GetParkingConfigurationUseCase
import com.complexparking.domain.useCase.UpdateParkingConfigurationUseCase
import com.complexparking.entities.ParkingConfiguration
import com.complexparking.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ParkingSettingsScreenViewModel(
    private val getParkingConfigurationUseCase: GetParkingConfigurationUseCase,
    private val updateParkingConfigurationUseCase: UpdateParkingConfigurationUseCase
) : BaseViewModel() {

    private val _parkingSettingsState = MutableStateFlow(ParkingSettingsState())
    val parkingSettingsModel get() = _parkingSettingsState.asStateFlow()

    override fun onStartScreen() {
        //_parkingSettingsState.value = ParkingSettingsState()
        loadParkingData()
    }

    private fun loadParkingData() {
        viewModelScope.launch {
            getParkingConfigurationUseCase.execute().collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    result?.let {
                        _parkingSettingsState.update {
                            it.copy(
                                currentParkingId = result.id,
                                parkingMaxFreeHour = result.maxFreeHour.toString(),
                                parkingHourPrice = result.parkingPrice.toString(),
                                isButtonEnabled = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onSaveSettings() {
        viewModelScope.launch {
            val parkingConfiguration = ParkingConfiguration(
                id = _parkingSettingsState.value.currentParkingId,
                parkingPrice = _parkingSettingsState.value.parkingHourPrice.toDouble(),
                maxFreeHour = _parkingSettingsState.value.parkingMaxFreeHour.toInt()
            )

            updateParkingConfigurationUseCase.execute(parkingConfiguration).collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    if(result) {
                        loadParkingData()
                    }
                }
            }
        }
    }

    fun clearForm(){
        _parkingSettingsState.update { it.copy(
            parkingHourPrice = "",
            parkingMaxFreeHour = "",
            isButtonEnabled = false
        ) }
    }

    fun onParkingHourPriceChange(parkingHourPrice: String) {
        _parkingSettingsState.update { it.copy(parkingHourPrice = parkingHourPrice) }
        validateParkingData()
    }

    fun onParkingMaxFreeHourChange(maxParkingHour: String) {
        _parkingSettingsState.update { it.copy(parkingMaxFreeHour = maxParkingHour) }
        validateParkingData()
    }

    private fun validateParkingData() {
        val result = if (
            _parkingSettingsState.value.parkingHourPrice.isEmpty() ||
            _parkingSettingsState.value.parkingMaxFreeHour.isEmpty()
        ) {
            false
        } else {
            true
        }
        _parkingSettingsState.update { it.copy(isButtonEnabled = result) }
    }
}