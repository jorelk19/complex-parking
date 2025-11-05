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
import com.complexparking.ui.settings.menuScreens.ParkingSettingsState
import com.complexparking.ui.utilities.ErrorType
import com.complexparking.ui.utilities.LinearProgressManager
import com.complexparking.ui.utilities.isValidEmail
import com.complexparking.ui.utilities.isValidPassword
import com.complexparking.utils.excelTools.FileData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    private val _wizardState = MutableStateFlow(WizardScreenState())
    val wizardState get() = _wizardState.asStateFlow()

    private val _parkingSettingState = MutableStateFlow(ParkingSettingsState())
    val parkingSettingState get() = _parkingSettingState.asStateFlow()


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
        _wizardState.value = WizardScreenState()
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
        _parkingSettingState.update {
            it.copy(
                parkingQuantity = parkingQuantity
            )
        }
        validateComplexConfiguration()
    }

    fun onAddressChange(address: String) {
        _parkingSettingState.update {
            it.copy(
                complexAddress = address
            )
        }
        validateComplexConfiguration()
    }

    fun onAdminChange(adminName: String) {
        _parkingSettingState.update {
            it.copy(
                adminName = adminName
            )
        }
        validateComplexConfiguration()
    }

    fun onUnitChange(unit: String) {
        _parkingSettingState.update {
            it.copy(
                quantityUnit = unit
            )
        }
        validateComplexConfiguration()
    }

    fun onComplexNameChange(name: String) {
        _parkingSettingState.update {
            it.copy(
                complexName = name
            )
        }
        validateComplexConfiguration()
    }

    private fun validateComplexConfiguration() {
        val isButtonEnabled = if (_parkingSettingState.value.complexName.isEmpty() ||
            _parkingSettingState.value.complexAddress.isEmpty() ||
            _parkingSettingState.value.parkingQuantity.isEmpty() ||
            _parkingSettingState.value.quantityUnit.isEmpty() ||
            _parkingSettingState.value.adminName.isEmpty() ||
            _parkingSettingState.value.parkingHourPrice.isEmpty() ||
            _parkingSettingState.value.parkingMaxHourFree.isEmpty()
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
        _wizardState.update {
            it.copy(
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
        }
        validateUploadComplexData()
    }

    private fun onSaveComplexDataAction() {
        viewModelScope.launch {
            loadComplexUnitDataUseCase.execute(
                ComplexData(
                    complexUnit = _parkingSettingState.value.quantityUnit.toInt(),
                    complexName = _parkingSettingState.value.complexName,
                    complexAddress = _parkingSettingState.value.complexAddress,
                    parkingQuantity = _parkingSettingState.value.parkingQuantity.toInt(),
                    parkingPrice = _parkingSettingState.value.parkingHourPrice.toDouble(),
                    parkingMaxFreeHour = _parkingSettingState.value.parkingMaxHourFree.toInt(),
                    adminEmail = _wizardState.value.adminEmail,
                    adminPassword = _wizardState.value.adminPassword,
                    adminName = _parkingSettingState.value.adminName,
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
        _wizardState.update {
            it.copy(
                pathFile = uri,
                uploadButtonVisibility = path.isNotEmpty()
            )
        }
    }

    private fun updateStep() {
        val step = when (currentIndex.value) {
            0 -> {
                setButtonEnabled(false)
                validateComplexConfiguration()
                EnumWizardStep.STEP1
            }

            1 -> {
                _wizardState.update {
                    it.copy(
                        searchButtonEnabled = _permissionsGranted.value
                    )
                }
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
            !_wizardState.value.errorAdminPassword &&
            _wizardState.value.adminPassword.isNotEmpty() &&
            !_wizardState.value.adminEmailError &&
            _wizardState.value.adminEmail.isNotEmpty() &&
            !_wizardState.value.errorRepeatAdminPassword &&
            _wizardState.value.repeatAdminPassword.isNotEmpty()
        ) {
            setButtonEnabled(true)
        } else {
            setButtonEnabled(false)
        }
    }

    fun onAdminEmailChange(email: String) {
        email.isValidEmail(
            onTrue = {
                _wizardState.update {
                    it.copy(
                        adminEmailError = false,
                        adminEmailErrorType = ErrorType.NONE,
                        adminEmail = email
                    )
                }
            },
            onFalse = {
                _wizardState.update {
                    it.copy(
                        adminEmailError = true,
                        adminEmailErrorType = ErrorType.INVALID_EMAIL
                    )
                }
            }
        )
        if (_wizardState.value.adminEmail == _wizardState.value.userEmail && _wizardState.value.userEmail.isNotEmpty()) {
            _wizardState.update {
                it.copy(
                    adminEmailError = true,
                    adminEmailErrorType = ErrorType.USER_ADMIN_SAME_EMAIL
                )
            }
        }
        validateUserCreation()
    }

    fun onAdminPasswordChange(password: String) {
        password.isValidPassword(
            onTrue = {
                _wizardState.update {
                    it.copy(
                        errorAdminPassword = false,
                        adminPasswordErrorType = ErrorType.NONE,
                        adminPassword = password
                    )
                }
            },
            onFalse = {
                _wizardState.update {
                    it.copy(
                        errorAdminPassword = true,
                        adminPasswordErrorType = ErrorType.INVALID_PASSWORD
                    )
                }
            }
        )
        if (_wizardState.value.adminPassword != _wizardState.value.repeatAdminPassword && _wizardState.value.repeatAdminPassword.isNotEmpty()) {
            _wizardState.update {
                it.copy(
                    errorAdminPassword = true,
                    adminPasswordErrorType = ErrorType.REPEAT_PASSWORD_INVALID
                )
            }
        }
        validateUserCreation()
    }

    fun onRepeatAdminPasswordChange(password: String) {
        password.isValidPassword(
            onTrue = {
                _wizardState.update {
                    it.copy(
                        errorRepeatAdminPassword = false,
                        repeatAdminPasswordErrorType = ErrorType.NONE,
                        repeatAdminPassword = password
                    )
                }
            },
            onFalse = {
                _wizardState.update {
                    it.copy(
                        errorRepeatAdminPassword = true,
                        repeatAdminPasswordErrorType = ErrorType.INVALID_PASSWORD
                    )
                }
            }
        )
        if (_wizardState.value.adminPassword != _wizardState.value.repeatAdminPassword && _wizardState.value.adminPassword.isNotEmpty()) {
            _wizardState.update {
                it.copy(
                    errorRepeatAdminPassword = true,
                    repeatAdminPasswordErrorType = ErrorType.REPEAT_PASSWORD_INVALID
                )
            }
        }
        validateUserCreation()
    }

    fun onUserEmailChange(email: String) {
        email.isValidEmail(
            onTrue = {
                _wizardState.update {
                    it.copy(
                        userEmailError = false,
                        userEmailErrorType = ErrorType.NONE,
                        userEmail = email
                    )
                }
            },
            onFalse = {
                _wizardState.update {
                    it.copy(
                        userEmailError = true,
                        userEmailErrorType = ErrorType.INVALID_EMAIL
                    )
                }
            }
        )
        if (_wizardState.value.adminEmail == _wizardState.value.userEmail && _wizardState.value.adminEmail.isNotEmpty()) {
            _wizardState.update {
                it.copy(
                    userEmailError = true,
                    userEmailErrorType = ErrorType.USER_ADMIN_SAME_EMAIL
                )
            }
        }
        validateUserCreation()
    }

    fun onUserPasswordChange(password: String) {
        password.isValidPassword(
            onTrue = {
                _wizardState.update {
                    it.copy(
                        errorUserPassword = false,
                        userPasswordErrorType = ErrorType.NONE,
                        userPassword = password
                    )
                }
            },
            onFalse = {
                _wizardState.update {
                    it.copy(
                        errorUserPassword = true,
                        userPasswordErrorType = ErrorType.INVALID_PASSWORD
                    )
                }
            }
        )
        if (_wizardState.value.userPassword != _wizardState.value.repeatUserPassword && _wizardState.value.repeatUserPassword.isNotEmpty()) {
            _wizardState.update {
                it.copy(
                    errorUserPassword = true,
                    userPasswordErrorType = ErrorType.REPEAT_PASSWORD_INVALID
                )
            }
        }
        validateUserCreation()
    }

    fun onRepeatUserPasswordChange(password: String) {
        password.isValidPassword(
            onTrue = {
                _wizardState.update {
                    it.copy(
                        errorRepeatUserPassword = false,
                        repeatUserPasswordErrorType = ErrorType.NONE,
                        repeatUserPassword = password
                    )
                }
            },
            onFalse = {
                _wizardState.update {
                    it.copy(
                        errorRepeatUserPassword = true,
                        repeatUserPasswordErrorType = ErrorType.INVALID_PASSWORD
                    )
                }
            }
        )
        if (_wizardState.value.userPassword != _wizardState.value.repeatUserPassword && _wizardState.value.userPassword.isNotEmpty()) {
            _wizardState.update {
                it.copy(
                    errorRepeatUserPassword = true,
                    repeatUserPasswordErrorType = ErrorType.REPEAT_PASSWORD_INVALID
                )
            }
        }
        validateUserCreation()
    }

    fun onParkingHourPriceChange(parkingPrice: String) {
        _parkingSettingState.update {
            it.copy(
                parkingHourPrice = parkingPrice
            )
        }
        validateComplexConfiguration()
    }

    fun onParkingMaxFreeHourChange(maxHour: String) {
        _parkingSettingState.update {
            it.copy(
                parkingMaxHourFree = maxHour
            )
        }
        validateComplexConfiguration()
    }
}