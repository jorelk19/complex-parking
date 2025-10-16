package com.complexparking.ui.login

import com.complexparking.ui.utilities.ErrorType
import kotlinx.serialization.Serializable

@Serializable
data class LoginScreenModel(
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val onClickAccess: () -> Unit = {},
    val emailErrorType: ErrorType = ErrorType.NONE,
    val passwordErrorType: ErrorType = ErrorType.NONE,
    val isButtonAccessEnabled: Boolean = false
)
