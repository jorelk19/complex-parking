package com.complexparking.ui.wizard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.complexparking.R
import com.complexparking.domain.interfaces.ISplashScreenUseCase
import kotlinx.coroutines.launch

class WizardScreenViewModel(
    private val splashScreenUseCase: ISplashScreenUseCase
): ViewModel() {
    private val _gotoConfigComplex = mutableStateOf(false)
    val gotoConfigComplex get() = _gotoConfigComplex
    private val _gotoUploadUnitsData = mutableStateOf(false)
    val gotoUploadUnitsData get() = _gotoUploadUnitsData
    private val _gotoLoginScreen = mutableStateOf(false)
    val gotoLoginScreen get() = _gotoLoginScreen

    private val _currentIndex = mutableStateOf(0)
    val currentIndex get() = _currentIndex

    private val _currentStep = mutableStateOf(EnumWizardStep.STEP1)
    val currentStep get() = _currentStep

    private val _wizardModel = mutableStateOf(WizardScreenModel())
    val wizardModel get() = _wizardModel

    private val _isButtonPreviousVisible = mutableStateOf(false)
    val isButtonPreviousVisible get() = _isButtonPreviousVisible

    init {
        loadData()
    }

    private fun loadData() {
        _wizardModel.value = WizardScreenModel(
            quantityUnit = "",
            complexName = "",
            complexAddress = "",
            parkingQuantity = "",
            isButtonEnabled = false,
            buttonText = R.string.wizard_complex_configuration_next_button,
            onComplexNameChange = { onComplexNameChange(it) },
            onUnitChange = { onUnitChange(it) },
            onAddressChange = { onAddressChange(it) },
            onParkingChange = { onParkingChange(it) },
            onClickNextStep = { onClickNextStep(it) },
            onClickPreviousStep = { onClickPreviousStep(it) }
        )
    }

    private fun onParkingChange(parkingQuantity: String) {
        _wizardModel.value = _wizardModel.value.copy(
            parkingQuantity = parkingQuantity
        )
        validateComplexConfiguration()
    }

    private fun onAddressChange(address: String) {
        _wizardModel.value = _wizardModel.value.copy(
            complexAddress = address
        )
        validateComplexConfiguration()
    }

    private fun onUnitChange(unit: String) {
        _wizardModel.value = _wizardModel.value.copy(
            quantityUnit = unit
        )
        validateComplexConfiguration()
    }

    private fun onComplexNameChange(name: String) {
        _wizardModel.value = _wizardModel.value.copy(
            complexName = name
        )
        validateComplexConfiguration()
    }

    private fun onClickNextStep(step: EnumWizardStep) {
        when(step) {
            EnumWizardStep.STEP1 -> {
                //validateComplexConfiguration()
                _wizardModel.value = _wizardModel.value.copy(
                    buttonText = R.string.wizard_complex_configuration_next_button
                )
            }
            EnumWizardStep.STEP2 -> {
                _wizardModel.value = _wizardModel.value.copy(
                    buttonText = R.string.wizard_complex_configuration_finish_button
                )
                _wizardModel.value = _wizardModel.value.copy(
                    isButtonEnabled = false
                )
                //validateUploadComplexData()
            }
            EnumWizardStep.STEP3 -> {
                finishWizardFlow()
                //validateUserCreation()
            }
        }
        evaluateNextIndex()
    }

    private fun evaluateNextIndex() {
        if(currentIndex.value < 2) {
            currentIndex.value = currentIndex.value + 1
            _isButtonPreviousVisible.value = true
        }
        updateStep()
    }

    private fun onClickPreviousStep(step: EnumWizardStep) {
        when(step) {
            EnumWizardStep.STEP1 -> {
                _wizardModel.value = _wizardModel.value.copy(
                    buttonText = R.string.wizard_complex_configuration_next_button
                )

            }
            EnumWizardStep.STEP2 -> {
                _wizardModel.value = _wizardModel.value.copy(
                    buttonText = R.string.wizard_complex_configuration_next_button
                )
            }
            EnumWizardStep.STEP3 -> {}
        }
        evaluatePreviousIndex()
    }

    private fun evaluatePreviousIndex() {
        if(currentIndex.value > 0) {
            currentIndex.value = currentIndex.value -1
            if(currentIndex.value == 0) {
                _isButtonPreviousVisible.value = false
            } else {
                _isButtonPreviousVisible.value = true
            }
            _wizardModel.value = _wizardModel.value.copy(
                buttonText = R.string.wizard_complex_configuration_next_button
            )
        }
        updateStep()
    }

    private fun updateStep() {
        _currentStep.value = when (currentIndex.value) {
            0 -> EnumWizardStep.STEP1
            1 -> EnumWizardStep.STEP2
            2 -> EnumWizardStep.STEP3
            else -> EnumWizardStep.STEP1
        }
    }

    private fun finishWizardFlow() {
        viewModelScope.launch {
            runCatching {
                splashScreenUseCase.wizardComplete()
            }.onSuccess {
                _gotoLoginScreen.value = true
            }
        }
    }

    private fun validateComplexConfiguration() {
        val isButtonEnabled = if(_wizardModel.value.complexName.isEmpty() ||
            _wizardModel.value.complexAddress.isEmpty() ||
            _wizardModel.value.parkingQuantity.isEmpty() ||
            _wizardModel.value.quantityUnit.isEmpty()
            ) {
            false
        } else {
            true
        }
        _wizardModel.value = _wizardModel.value.copy(
            isButtonEnabled = isButtonEnabled
        )
    }

    private fun validateUploadComplexData() {

    }

    private fun validateUserCreation() {

    }
}