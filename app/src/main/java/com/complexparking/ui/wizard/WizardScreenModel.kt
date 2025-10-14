package com.complexparking.ui.wizard

import android.net.Uri
import com.complexparking.ui.utilities.ErrorType
import com.complexparking.utils.tools.FileData

data class WizardScreenModel(
    val complexName: String = "",
    val quantityUnit: String = "",
    val complexAddress: String = "",
    val parkingQuantity: String = "",
    val pathFile: Uri? = null,
    val uploadButtonVisibility: Boolean = false,
    val previousList: ArrayList<PreviousFileData> = arrayListOf(),
    val showPreviousList: Boolean = false,
    val errorAdminPassword: Boolean = false,
    val errorRepeatAdminPassword: Boolean = false,
    val errorUserPassword: Boolean = false,
    val errorRepeatUserPassword: Boolean = false,
    val adminEmailError: Boolean = false,
    val userEmailError: Boolean = false,
    val adminEmailErrorType: ErrorType = ErrorType.NONE,
    val userEmailErrorType: ErrorType = ErrorType.NONE,
/*    val onComplexNameChange: (String) -> Unit = {},
    val onUnitChange: (String) -> Unit = {},
    val onAddressChange: (String) -> Unit = {},
    val onParkingChange: (String) -> Unit = {},
    val onSearchFileButton: (Uri) -> Unit = {},
    val onClickNextStep: (EnumWizardStep) -> Unit = {},
    val onClickPreviousStep: (EnumWizardStep) -> Unit = {},
    val onUploadFileClick: (ArrayList<FileData>) -> Unit = {},
    val onAdminPasswordChange: (String) -> Unit = {},
    val onRepeatAdminPasswordChange: (String) -> Unit = {},
    val onUserPasswordChange: (String) -> Unit = {},
    val onRepeatUserPasswordChange: (String) -> Unit = {},
    val onAdminEmailChange: (String) -> Unit = {},
    val onUserEmailChange: (String) -> Unit = {}*/
)
