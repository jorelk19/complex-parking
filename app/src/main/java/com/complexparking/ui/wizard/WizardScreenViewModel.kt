package com.complexparking.ui.wizard

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.R
import com.complexparking.domain.useCase.GetPermissionsUseCase
import com.complexparking.domain.useCase.LoadComplexUnitDataUseCase
import com.complexparking.domain.useCase.SplashScreenSetWizardCompleteUseCase
import com.complexparking.entities.ComplexData
import com.complexparking.ui.controls.SnackBarController
import com.complexparking.ui.controls.SnackBarEvents
import com.complexparking.ui.utilities.ErrorType
import com.complexparking.ui.utilities.LinearProgressManager
import com.complexparking.ui.utilities.isValidEmail
import com.complexparking.ui.utilities.isValidPassword
import com.complexparking.utils.excelTools.FileData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WizardScreenViewModel(
    private val splashScreenSetWizardCompleteUseCase: SplashScreenSetWizardCompleteUseCase,
    private val loadComplexUnitDataUseCase: LoadComplexUnitDataUseCase,
    private val getPermissionsUseCase: GetPermissionsUseCase,
) : BaseWizardViewModel() {
    private val _gotoConfigComplex = mutableStateOf(false)
    private val _gotoUploadUnitsData = mutableStateOf(false)
    private val _gotoLoginScreen = mutableStateOf(false)
    val gotoLoginScreen get() = _gotoLoginScreen
    private val _currentStep = mutableStateOf(EnumWizardStep.STEP1)
    private val _fileData = mutableStateOf(ArrayList<FileData>())
    private val _wizardModel = MutableStateFlow(WizardScreenState())
    val wizardModel get() = _wizardModel.asStateFlow()

    private val _permissionsGranted = mutableStateOf(false)

    override fun onStartWizard() {
        loadData()
        setIndexChangeAction {
            if (!isFinalStep.value) {
                updateStep()
            } else {
                onSaveComplexDataAction()
            }
        }
    }

    private fun loadData() {
        _wizardModel.value = WizardScreenState()
        viewModelScope.launch {
            getPermissionsUseCase.execute().collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    result?.let {
                        _permissionsGranted.value = it
                    }
                }
            }
        }
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

    fun onAdminChange(adminName: String) {
        _wizardModel.value = _wizardModel.value.copy(
            adminName = adminName
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
            _wizardModel.value.quantityUnit.isEmpty() ||
            _wizardModel.value.adminName.isEmpty()
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
                    complexUnit = it.unit,
                    residentName = it.name,
                    residentLastName = it.lastName,
                    plate = it.plate
                )
            }),
            showPreviousList = fileData.isNotEmpty()
        )
        validateUploadComplexData()
    }

    private fun onSaveComplexDataAction() {
        viewModelScope.launch {
            loadComplexUnitDataUseCase.execute(
                ComplexData(
                    complexUnit = _wizardModel.value.quantityUnit.toInt(),
                    complexName = _wizardModel.value.complexName,
                    complexAddress = _wizardModel.value.complexAddress,
                    parkingQuantity = _wizardModel.value.parkingQuantity.toInt(),
                    parkingPrice = _wizardModel.value.parkingHourPrice.toDouble(),
                    parkingMaxFreeHour = _wizardModel.value.parkingMaxHourFree.toInt(),
                    adminEmail = _wizardModel.value.adminEmail,
                    adminPassword = _wizardModel.value.adminPassword,
                    adminName = _wizardModel.value.adminName,
                    fileDataList = _fileData.value
                )
            ).collect { resultUseCase ->
                validateUseCaseResult(resultUseCase) { result ->
                    if (result) {
                        _gotoLoginScreen.value = result
                        /*Show snackbar*/
                    }
                }
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

    private fun updateStep() {
        val step = when (currentIndex.value) {
            0 -> {
                setButtonEnabled(false)
                validateComplexConfiguration()
                EnumWizardStep.STEP1
            }

            1 -> {
                _wizardModel.value = _wizardModel.value.copy(
                    searchButtonEnabled = _permissionsGranted.value
                )
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

    private fun validateUploadComplexData() {
        val isValid = _fileData.value.isNotEmpty()
        setButtonEnabled(isValid)
        LinearProgressManager.hideLoader()
        if (isValid) {
            viewModelScope.launch {
                SnackBarController.sendEvent(
                    SnackBarEvents(
                        titleId = R.string.wizard_user_creation_snack_success_title,
                        messageId = R.string.wizard_user_creation_snack_success_message,
                        iconId = R.drawable.ic_circle_check,
                        buttonIconId = R.drawable.ic_close
                    )
                )
            }
        }
    }

    private fun validateUserCreation() {
        if (
            !_wizardModel.value.errorAdminPassword &&
            _wizardModel.value.adminPassword.isNotEmpty() &&
            !_wizardModel.value.adminEmailError &&
            _wizardModel.value.adminEmail.isNotEmpty() &&
            !_wizardModel.value.errorRepeatAdminPassword &&
            _wizardModel.value.repeatAdminPassword.isNotEmpty()
        ) {
            setButtonEnabled(true)
        } else {
            setButtonEnabled(false)
        }
    }

    fun onAdminEmailChange(email: String) {
        email.isValidEmail(
            onTrue = {
                _wizardModel.value = _wizardModel.value.copy(
                    adminEmailError = false,
                    adminEmailErrorType = ErrorType.NONE,
                    adminEmail = it
                )
            },
            onFalse = {
                _wizardModel.value = _wizardModel.value.copy(
                    adminEmailError = true,
                    adminEmailErrorType = ErrorType.INVALID_EMAIL
                )
            }
        )
        if (_wizardModel.value.adminEmail == _wizardModel.value.userEmail && _wizardModel.value.userEmail.isNotEmpty()) {
            _wizardModel.value = _wizardModel.value.copy(
                adminEmailError = true,
                adminEmailErrorType = ErrorType.USER_ADMIN_SAME_EMAIL
            )
        }
        validateUserCreation()
    }

    fun onAdminPasswordChange(password: String) {
        password.isValidPassword(
            onTrue = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorAdminPassword = false,
                    adminPasswordErrorType = ErrorType.NONE,
                    adminPassword = it
                )
            },
            onFalse = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorAdminPassword = true,
                    adminPasswordErrorType = ErrorType.INVALID_PASSWORD
                )
            }
        )
        if (_wizardModel.value.adminPassword != _wizardModel.value.repeatAdminPassword && _wizardModel.value.repeatAdminPassword.isNotEmpty()) {
            _wizardModel.value = _wizardModel.value.copy(
                errorAdminPassword = true,
                adminPasswordErrorType = ErrorType.REPEAT_PASSWORD_INVALID
            )
        }
        validateUserCreation()
    }

    fun onRepeatAdminPasswordChange(password: String) {
        password.isValidPassword(
            onTrue = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorRepeatAdminPassword = false,
                    repeatAdminPasswordErrorType = ErrorType.NONE,
                    repeatAdminPassword = it
                )
            },
            onFalse = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorRepeatAdminPassword = true,
                    repeatAdminPasswordErrorType = ErrorType.INVALID_PASSWORD
                )
            }
        )
        if (_wizardModel.value.adminPassword != _wizardModel.value.repeatAdminPassword && _wizardModel.value.adminPassword.isNotEmpty()) {
            _wizardModel.value = _wizardModel.value.copy(
                errorRepeatAdminPassword = true,
                repeatAdminPasswordErrorType = ErrorType.REPEAT_PASSWORD_INVALID
            )
        }
        validateUserCreation()
    }

    fun onUserEmailChange(email: String) {
        email.isValidEmail(
            onTrue = {
                _wizardModel.value = _wizardModel.value.copy(
                    userEmailError = false,
                    userEmailErrorType = ErrorType.NONE,
                    userEmail = it
                )
            },
            onFalse = {
                _wizardModel.value = _wizardModel.value.copy(
                    userEmailError = true,
                    userEmailErrorType = ErrorType.INVALID_EMAIL
                )
            }
        )
        if (_wizardModel.value.adminEmail == _wizardModel.value.userEmail && _wizardModel.value.adminEmail.isNotEmpty()) {
            _wizardModel.value = _wizardModel.value.copy(
                userEmailError = true,
                userEmailErrorType = ErrorType.USER_ADMIN_SAME_EMAIL
            )
        }
        validateUserCreation()
    }

    fun onUserPasswordChange(password: String) {
        password.isValidPassword(
            onTrue = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorUserPassword = false,
                    userPasswordErrorType = ErrorType.NONE,
                    userPassword = it
                )
            },
            onFalse = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorUserPassword = true,
                    userPasswordErrorType = ErrorType.INVALID_PASSWORD
                )
            }
        )
        if (_wizardModel.value.userPassword != _wizardModel.value.repeatUserPassword && _wizardModel.value.repeatUserPassword.isNotEmpty()) {
            _wizardModel.value = _wizardModel.value.copy(
                errorUserPassword = true,
                userPasswordErrorType = ErrorType.REPEAT_PASSWORD_INVALID
            )
        }
        validateUserCreation()
    }

    fun onRepeatUserPasswordChange(password: String) {
        password.isValidPassword(
            onTrue = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorRepeatUserPassword = false,
                    repeatUserPasswordErrorType = ErrorType.NONE,
                    repeatUserPassword = it
                )
            },
            onFalse = {
                _wizardModel.value = _wizardModel.value.copy(
                    errorRepeatUserPassword = true,
                    repeatUserPasswordErrorType = ErrorType.INVALID_PASSWORD
                )
            }
        )
        if (_wizardModel.value.userPassword != _wizardModel.value.repeatUserPassword && _wizardModel.value.userPassword.isNotEmpty()) {
            _wizardModel.value = _wizardModel.value.copy(
                errorRepeatUserPassword = true,
                repeatUserPasswordErrorType = ErrorType.REPEAT_PASSWORD_INVALID
            )
        }
        validateUserCreation()
    }

    fun onParkingHourPriceChange(parkingPrice: String) {
        _wizardModel.value = _wizardModel.value.copy(
            parkingHourPrice = parkingPrice
        )
        validateComplexConfiguration()
    }

    fun onParkingMaxFreeHourChange(maxHour: String) {
        _wizardModel.value = _wizardModel.value.copy(
            parkingMaxHourFree = maxHour
        )
        validateComplexConfiguration()
    }
}