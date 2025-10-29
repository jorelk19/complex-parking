package com.complexparking.ui.user

import com.complexparking.ui.utilities.ErrorType

data class CreateUserState(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isAdmin: Boolean = false,
    val errorPassword: Boolean = false,
    val passwordErrorType: ErrorType = ErrorType.NONE,
    val errorRepeatPassword: Boolean = false,
    val repeatPasswordErrorType: ErrorType = ErrorType.NONE,
    val userEmailError: Boolean = false,
    val userEmailErrorType: ErrorType = ErrorType.NONE,
    val isButtonCreateEnabled: Boolean = false
)
