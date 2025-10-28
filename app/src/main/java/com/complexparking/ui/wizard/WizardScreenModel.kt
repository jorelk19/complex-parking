package com.complexparking.ui.wizard

import android.net.Uri
import com.complexparking.ui.utilities.ErrorType

data class WizardScreenModel(
    val complexName: String = "",
    val quantityUnit: String = "",
    val complexAddress: String = "",
    val parkingQuantity: String = "",
    val adminName: String = "",
    val pathFile: Uri? = null,
    val userPassword: String = "",
    val adminPassword: String = "",
    val repeatUserPassword: String = "",
    val repeatAdminPassword: String = "",
    val adminEmail: String = "",
    val userEmail: String = "",
    val uploadButtonVisibility: Boolean = false,
    val searchButtonEnabled: Boolean = false,
    val previousList: ArrayList<PreviousFileData> = arrayListOf(),
    val showPreviousList: Boolean = false,
    val errorAdminPassword: Boolean = false,
    val adminPasswordErrorType: ErrorType = ErrorType.NONE,
    val errorRepeatAdminPassword: Boolean = false,
    val repeatAdminPasswordErrorType: ErrorType = ErrorType.NONE,
    val errorUserPassword: Boolean = false,
    val userPasswordErrorType: ErrorType = ErrorType.NONE,
    val errorRepeatUserPassword: Boolean = false,
    val repeatUserPasswordErrorType: ErrorType = ErrorType.NONE,
    val adminEmailError: Boolean = false,
    val userEmailError: Boolean = false,
    val adminEmailErrorType: ErrorType = ErrorType.NONE,
    val userEmailErrorType: ErrorType = ErrorType.NONE,
)
