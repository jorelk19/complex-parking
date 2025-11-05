package com.complexparking.ui.settings.menuScreens

import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.GetComplexConfigurationUseCase
import com.complexparking.domain.useCase.UpdateParkingConfigurationUseCase
import com.complexparking.entities.ComplexConfiguration
import com.complexparking.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ParkingSettingsScreenViewModel(
    private val getComplexConfigurationUseCase: GetComplexConfigurationUseCase,
    private val updateParkingConfigurationUseCase: UpdateParkingConfigurationUseCase
) : BaseViewModel() {

    private val _parkingSettingsState = MutableStateFlow(ParkingSettingsState())
    val parkingSettingsModel get() = _parkingSettingsState.asStateFlow()

    override fun onStartScreen() {
        loadParkingData()
    }

    private fun loadParkingData() {
        viewModelScope.launch {
            getComplexConfigurationUseCase.execute().collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    result?.let {
                        _parkingSettingsState.update {
                            it.copy(
                                currentParkingId = result.id,
                                parkingMaxHourFree = result.maxFreeHour.toString(),
                                parkingHourPrice = result.parkingPrice.toString(),
                                parkingQuantity = result.complexQuantityParking.toString(),
                                complexName = result.complexName,
                                adminName = result.adminName,
                                complexAddress = result.complexAddress,
                                quantityUnit = result.complexUnits.toString(),
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
            val complexConfiguration = ComplexConfiguration(
                id = _parkingSettingsState.value.currentParkingId,
                parkingPrice = _parkingSettingsState.value.parkingHourPrice.toDouble(),
                maxFreeHour = _parkingSettingsState.value.parkingMaxHourFree.toInt(),
                adminName = _parkingSettingsState.value.adminName,
                complexAddress = _parkingSettingsState.value.complexAddress,
                complexQuantityParking = _parkingSettingsState.value.parkingQuantity.toInt(),
                complexUnits = _parkingSettingsState.value.quantityUnit.toInt(),
                complexName = _parkingSettingsState.value.complexName
            )

            updateParkingConfigurationUseCase.execute(complexConfiguration).collect { resultUseCaseState ->
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
            parkingMaxHourFree = "",
            isButtonEnabled = false
        ) }
    }

    fun onParkingHourPriceChange(parkingHourPrice: String) {
        _parkingSettingsState.update { it.copy(parkingMaxHourFree = parkingHourPrice) }
        validateParkingData()
    }

    fun onParkingMaxFreeHourChange(maxParkingHour: String) {
        _parkingSettingsState.update { it.copy(parkingMaxHourFree = maxParkingHour) }
        validateParkingData()
    }

    fun onParkingChange(parkingQuantity: String) {
        _parkingSettingsState.value = _parkingSettingsState.value.copy(
            parkingQuantity = parkingQuantity
        )
        validateParkingData()
    }

    fun onAddressChange(address: String) {
        _parkingSettingsState.value = _parkingSettingsState.value.copy(
            complexAddress = address
        )
        validateParkingData()
    }

    fun onAdminChange(adminName: String) {
        _parkingSettingsState.value = _parkingSettingsState.value.copy(
            adminName = adminName
        )
        validateParkingData()
    }

    fun onUnitChange(unit: String) {
        _parkingSettingsState.value = _parkingSettingsState.value.copy(
            quantityUnit = unit
        )
        validateParkingData()
    }

    fun onComplexNameChange(name: String) {
        _parkingSettingsState.value = _parkingSettingsState.value.copy(
            complexName = name
        )
        validateParkingData()
    }

    private fun validateParkingData() {
        val result = if (
            _parkingSettingsState.value.parkingMaxHourFree.isEmpty() ||
            _parkingSettingsState.value.parkingHourPrice.isEmpty() ||
            _parkingSettingsState.value.complexName.isEmpty() ||
            _parkingSettingsState.value.complexAddress.isEmpty() ||
            _parkingSettingsState.value.parkingQuantity.isEmpty() ||
            _parkingSettingsState.value.quantityUnit.isEmpty() ||
            _parkingSettingsState.value.adminName.isEmpty()
        ) {
            false
        } else {
            true
        }
        _parkingSettingsState.update { it.copy(isButtonEnabled = result) }
    }
}