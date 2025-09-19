package com.complexparking.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.interfaces.ILoginUseCase
import com.complexparking.domain.useCase.LoginState
import com.complexparking.ui.utilities.ErrorType
import com.complexparking.ui.utilities.isValidEmail
import com.complexparking.ui.utilities.isValidPassword
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    val loginUseCase: ILoginUseCase,
) : ViewModel() {
    var loginScreenModel = mutableStateOf(LoginScreenModel())
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val goToHome = mutableStateOf(false)

    init {
        loadLoginModel()
    }

    private fun loadLoginModel() {
        loginScreenModel.value = LoginScreenModel(
            onTextEmailChange = { onEmailChange(it) },
            onTextPasswordChange = { onPasswordChange(it) },
            onClickAccess = { onClickAccess() },
            emailError = false,
            passwordError = false,
            isButtonAccessEnabled = false
        )
    }

    private fun onClickAccess() {
        viewModelScope.launch {
            runCatching {
                loginUseCase.validateUser(email.value, password.value)
            }.onSuccess {
                when(it){
                    LoginState.LoginError -> {

                    }
                    LoginState.LoginSuccess -> {
                        goToHome.value = true
                    }
                }
            }.onFailure {

            }
        }

    }

    private fun onEmailChange(text: String) {
        email.value = text
        text.isValidEmail(
            onFalse = {
                loginScreenModel.value = loginScreenModel.value.copy(
                    emailErrorType = ErrorType.INVALID_EMAIL,
                    emailError = true
                )
            },
            onEmptyString = {
                loginScreenModel.value = loginScreenModel.value.copy(
                    emailErrorType = ErrorType.NONE,
                    emailError = true
                )
            },
            onTrue = {
                loginScreenModel.value = loginScreenModel.value.copy(
                    emailErrorType = ErrorType.NONE,
                    emailError = false
                )
            }
        )
        validateButton()
    }

    private fun onPasswordChange(text: String) {
        password.value = text
        text.isValidPassword(
            onFalse = {
                loginScreenModel.value = loginScreenModel.value.copy(
                    passwordErrorType = ErrorType.INVALID_PASSWORD,
                    passwordError = true
                )
            },
            onEmptyString = {
                loginScreenModel.value = loginScreenModel.value.copy(
                    passwordErrorType = ErrorType.NONE,
                    passwordError = true
                )
            },
            onTrue = {
                loginScreenModel.value = loginScreenModel.value.copy(
                    passwordErrorType = ErrorType.NONE,
                    passwordError = false
                )
            }
        )
        validateButton()
    }

    private fun validateButton() {
        val enabled = if (loginScreenModel.value.emailError || loginScreenModel.value.passwordError) {
            false
        } else {
            true
        }
        loginScreenModel.value = loginScreenModel.value.copy(
            isButtonAccessEnabled = enabled && email.value.isNotEmpty() && password.value.isNotEmpty()
        )
    }
}