package com.complexparking.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.domain.useCase.LoginUseCase
import com.complexparking.entities.LoginDataAccess
import com.complexparking.ui.base.BaseViewModel
import com.complexparking.ui.utilities.ErrorType
import com.complexparking.ui.utilities.isValidEmail
import com.complexparking.ui.utilities.isValidPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel() {
    var loginScreenModel = mutableStateOf(LoginScreenModel())
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val goToHome = mutableStateOf(false)

    private fun loadLoginModel() {
        loginScreenModel.value = LoginScreenModel(
            onClickAccess = { onClickAccess() },
            emailError = false,
            passwordError = false,
            isButtonAccessEnabled = false
        )
    }

    private fun onClickAccess() {
        viewModelScope.launch {
            loginUseCase.execute(
                LoginDataAccess(user = email.value, password = password.value)
            ).collect { resultUseCase ->
                validateUseCaseResult(resultUseCase) { result ->
                    if (result) {
                        goToHome.value = true
                    } else {
                        /*Show error*/
                    }
                }
            }
        }
    }

    fun onEmailChange(text: String) {
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

    fun onPasswordChange(text: String) {
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

    override fun onStartScreen() {
        loadLoginModel()
    }
}