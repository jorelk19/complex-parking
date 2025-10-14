package com.complexparking.ui.wizard

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.R
import com.complexparking.domain.interfaces.ILoadComplexUnitDataUseCase
import com.complexparking.domain.interfaces.ISplashScreenUseCase
import com.complexparking.ui.controls.SnackBarController
import com.complexparking.ui.controls.SnackBarEvents
import com.complexparking.ui.utilities.ErrorType
import com.complexparking.ui.utilities.LinearProgressManager
import com.complexparking.ui.utilities.isValidEmail
import com.complexparking.ui.utilities.isValidPassword
import com.complexparking.utils.tools.FileData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WizardScreenViewModel(
    private val splashScreenUseCase: ISplashScreenUseCase,
    private val loadComplexUnitDataUseCase: ILoadComplexUnitDataUseCase,
) : BaseWizardViewModel() {
    private val _gotoConfigComplex = mutableStateOf(false)
    val gotoConfigComplex get() = _gotoConfigComplex
    private val _gotoUploadUnitsData = mutableStateOf(false)
    val gotoUploadUnitsData get() = _gotoUploadUnitsData
    private val _gotoLoginScreen = mutableStateOf(false)
    val gotoLoginScreen get() = _gotoLoginScreen

    private val _currentStep = mutableStateOf(EnumWizardStep.STEP1)
    val currentStep get() = _currentStep

    private val _fileData = mutableStateOf(ArrayList<FileData>())
    val fileData get() = _fileData

    private val _wizardModel = MutableStateFlow(WizardScreenModel())
    val wizardModel get() = _wizardModel.asStateFlow()

    init {
        loadData()
        setIndexChangeAction {
            updateStep()
        }
    }

    private fun loadData() {
        _wizardModel.value = WizardScreenModel()
    }

    fun onParkingChange(parkingQuantity: String) {
        _wizardModel.value = _wizardModel.value.copy(
            parkingQuantity = parkingQuantity
        )
        validateComplexConfiguration()
    }

    fun onAddressChange(address: String) {
        _wizardModel.value = _wizardModel.value.copy(
            complexAddress = address
        )
        validateComplexConfiguration()
    }

    fun onUnitChange(unit: String) {
        _wizardModel.value = _wizardModel.value.copy(
            quantityUnit = unit
        )
        validateComplexConfiguration()
    }

    fun onComplexNameChange(name: String) {
        _wizardModel.value = _wizardModel.value.copy(
            complexName = name
        )
        validateComplexConfiguration()
    }

    private fun validateComplexConfiguration() {
        val isButtonEnabled = if (_wizardModel.value.complexName.isEmpty() ||
            _wizardModel.value.complexAddress.isEmpty() ||
            _wizardModel.value.parkingQuantity.isEmpty() ||
            _wizardModel.value.quantityUnit.isEmpty()
        ) {
            false
        } else {
            true
        }
        setButtonEnabled(isButtonEnabled)
    }

    fun onUploadDataFile(fileData: ArrayList<FileData>) {
        _fileData.value = fileData
        val data = if (fileData.size > 5) fileData.subList(0, 5) else fileData
        _wizardModel.value = _wizardModel.value.copy(
            previousList = ArrayList(data.map {
                PreviousFileData(
                    complexUnit = it.unit.toInt(),
                    residentName = it.name,
                    residentLastName = it.lastName,
                    plate = it.plate
                )
            }),
            showPreviousList = fileData.isNotEmpty()
        )
        validateUploadComplexData()
    }

    private fun onSaveFileDataAction(fileDataList: ArrayList<FileData>) {
        viewModelScope.launch {
            runCatching {

            }.onSuccess {

            }.onFailure {

            }
        }
    }

    fun onFileChange(uri: Uri?) {
        val path = uri?.path ?: ""
        _wizardModel.value = _wizardModel.value.copy(
            pathFile = uri,
            uploadButtonVisibility = path.isNotEmpty()
        )
    }

    /*private fun onClickNextStep(step: EnumWizardStep) {
        evaluateNextIndex()
        updateStep()
        when (step) {
            EnumWizardStep.STEP1 -> {
                validateComplexConfiguration()
                _wizardModel.value = _wizardModel.value.copy(
                    buttonText = R.string.wizard_complex_configuration_next_button
                )
            }

            EnumWizardStep.STEP2 -> {
                validateUploadComplexData()
                _wizardModel.value = _wizardModel.value.copy(
                    buttonText = R.string.wizard_complex_configuration_finish_button
                )
                _wizardModel.value = _wizardModel.value.copy(
                    isButtonEnabled = false
                )
            }

            EnumWizardStep.STEP3 -> {
                validateUserCreation()
                finishWizardFlow()
            }
        }
    }*/
    /*
        private fun onClickPreviousStep(step: EnumWizardStep) {
            when (step) {
                EnumWizardStep.STEP1 -> {
                    _wizardModel.value = _wizardModel.value.copy(
                        buttonText = R.string.wizard_complex_configuration_next_button
                    )
                    validateComplexConfiguration()
                }

                EnumWizardStep.STEP2 -> {
                    _wizardModel.value = _wizardModel.value.copy(
                        buttonText = R.string.wizard_complex_configuration_next_button
                    )
                    validateUploadComplexData()
                }

                EnumWizardStep.STEP3 -> {}
            }
            evaluatePreviousIndex()
        }*/

    private fun updateStep() {
        val step = when (currentIndex.value) {
            0 -> {
                setButtonEnabled(false)
                validateComplexConfiguration()
                EnumWizardStep.STEP1
            }

            1 -> {
                setButtonEnabled(false)
                validateUploadComplexData()
                EnumWizardStep.STEP2
            }

            2 -> {
                setButtonEnabled(false)

                validateUserCreation()
                EnumWizardStep.STEP3
            }

            else -> {
                setButtonEnabled(false)

                EnumWizardStep.STEP1
            }
        }
        _currentStep.value = step
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


    private fun validateUploadComplexData() {
        setButtonEnabled(_fileData.value.isNotEmpty())
        LinearProgressManager.hideLoader()
        viewModelScope.launch {
            SnackBarController.sendEvent(
                SnackBarEvents(
                    titleId = R.string.wizard_user_creation_snack_success_title,
                    subTitleId = R.string.wizard_user_creation_snack_success_message,
                    iconId = R.drawable.ic_circle_check,
                    buttonIconId = R.drawable.ic_close
                )
            )
        }
    }

    private fun validateUserCreation() {
        setButtonEnabled(true)
    }

    fun onAdminEmailChange(email: String) {
        email.isValidEmail(
            onTrue = {
                _wizardModel.value = _wizardModel.value.copy(
                    adminEmailError = false,
                    adminEmailErrorType = ErrorType.NONE
                )
            },
            onFalse = {
                _wizardModel.value = _wizardModel.value.copy(
                    adminEmailError = true,
                    adminEmailErrorType = ErrorType.INVALID_EMAIL
                )
            }
        )
    }
    fun onAdminPasswordChange(password: String) {
        password.isValidPassword(
            onTrue = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorAdminPassword = false,
                    adminPasswordErrorType = ErrorType.NONE
                )
            },
            onFalse = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorAdminPassword = true,
                    adminPasswordErrorType = ErrorType.INVALID_PASSWORD
                )
            }
        )
    }
    fun onRepeatAdminPasswordChange(password: String) {

    }
    fun onUserEmailChange(email: String) {
        email.isValidEmail(
            onTrue = {
                _wizardModel.value = _wizardModel.value.copy(
                    userEmailError = false,
                    userEmailErrorType = ErrorType.NONE
                )
            },
            onFalse = {
                _wizardModel.value = _wizardModel.value.copy(
                    userEmailError = true,
                    userEmailErrorType = ErrorType.INVALID_EMAIL
                )
            }
        )
    }
    fun onUserPasswordChange(password: String) {
        password.isValidPassword(
            onTrue = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorUserPassword = false,
                    userPasswordErrorType = ErrorType.NONE
                )
            },
            onFalse = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorUserPassword = true,
                    userPasswordErrorType = ErrorType.INVALID_PASSWORD
                )
            }
        )
    }
    fun onRepeatUserPasswordChange(password: String) {

    }
}