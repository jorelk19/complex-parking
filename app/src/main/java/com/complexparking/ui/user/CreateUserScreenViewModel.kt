package com.complexparking.ui.user

import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.CreateUserUseCase
import com.complexparking.entities.UserData
import com.complexparking.ui.base.BaseViewModel
import com.complexparking.ui.utilities.ErrorType
import com.complexparking.ui.utilities.isValidEmail
import com.complexparking.ui.utilities.isValidPassword
import java.util.Date
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateUserScreenViewModel(
    private val createUserUseCase: CreateUserUseCase
) : BaseViewModel() {
    private val _createUserState = MutableStateFlow(CreateUserState())
    val createUserState get() = _createUserState.asStateFlow()

    override fun onStartScreen() {
        _createUserState.update {
            it.copy(
                email = "",
                name = "",
                password = "",
                repeatPassword = "",
                errorPassword = false,
                errorRepeatPassword = false,
                userEmailError = false,
                isAdmin = false,
                isButtonCreateEnabled = false,
                userEmailErrorType = ErrorType.NONE,
                repeatPasswordErrorType = ErrorType.NONE,
                passwordErrorType = ErrorType.NONE
            )
        }
    }

    fun onNameChange(name: String) {
        _createUserState.update { it.copy(name = name) }
        validateUserData()
    }

    fun onPasswordChange(password: String) {
        password.isValidPassword(
            onTrue = {
                _createUserState.update {
                    it.copy(
                        password = password,
                        errorPassword = false,
                        passwordErrorType = ErrorType.NONE
                    )
                }
            },
            onFalse = {
                _createUserState.update {
                    it.copy(
                        errorPassword = true,
                        passwordErrorType = ErrorType.INVALID_PASSWORD
                    )
                }
            }
        )
        if (_createUserState.value.password != _createUserState.value.repeatPassword && _createUserState.value.repeatPassword.isNotEmpty()) {
            _createUserState.update {
                it.copy(
                    errorPassword = true,
                    passwordErrorType = ErrorType.REPEAT_PASSWORD_INVALID
                )
            }
        }
        validateUserData()
    }

    fun onRepeatPasswordChange(repeatPassword: String) {
        repeatPassword.isValidPassword(
            onTrue = {
                _createUserState.update {
                    it.copy(
                        repeatPassword = repeatPassword,
                        repeatPasswordErrorType = ErrorType.NONE,
                        errorRepeatPassword = false
                    )
                }
            },
            onFalse = {
                _createUserState.update {
                    it.copy(
                        repeatPasswordErrorType = ErrorType.INVALID_PASSWORD,
                        errorRepeatPassword = true
                    )
                }
            }
        )
        if (_createUserState.value.password != _createUserState.value.repeatPassword && _createUserState.value.password.isNotEmpty()) {
            _createUserState.update {
                it.copy(
                    repeatPasswordErrorType = ErrorType.REPEAT_PASSWORD_INVALID,
                    errorRepeatPassword = true
                )
            }
        }
        validateUserData()
    }

    fun onEmailChange(email: String) {
        email.isValidEmail(
            onTrue = {
                _createUserState.update {
                    it.copy(
                        email = email,
                        userEmailError = false,
                        userEmailErrorType = ErrorType.NONE
                    )
                }
            },
            onFalse = {
                _createUserState.update {
                    it.copy(
                        userEmailError = true,
                        userEmailErrorType = ErrorType.INVALID_EMAIL
                    )
                }
            }
        )
        validateUserData()
    }

    fun onCreateUserClick() {
        viewModelScope.launch {
            val userData = UserData(
                name = _createUserState.value.name,
                email = _createUserState.value.email,
                password = _createUserState.value.password,
                isAdmin = _createUserState.value.isAdmin,
                creationDate = Date()
            )
            createUserUseCase.execute(userData).collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    result?.let {
                        if (it) {
                            //Success message
                            onStartScreen()
                        } else {
                            //Error message
                        }
                    }
                }
            }
        }
    }

    private fun validateUserData() {
        val isValid = if (
            _createUserState.value.name.isEmpty() ||
            _createUserState.value.email.isEmpty() ||
            _createUserState.value.password.isEmpty() ||
            _createUserState.value.repeatPassword.isEmpty() ||
            _createUserState.value.userEmailError ||
            _createUserState.value.errorPassword ||
            _createUserState.value.errorRepeatPassword
        ) {
            false
        } else {
            true
        }
        _createUserState.update { it.copy(isButtonCreateEnabled = isValid) }
    }

    fun onCheckedAdmin(isAdmin  : Boolean) {
        _createUserState.update { it.copy(isAdmin = isAdmin) }
        validateUserData()
    }
}