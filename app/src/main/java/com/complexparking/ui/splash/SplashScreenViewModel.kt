package com.complexparking.ui.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.GetSessionUseCase
import com.complexparking.domain.useCase.ValidateWizardUseCase
import com.complexparking.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val validateWizardUseCase: ValidateWizardUseCase,
    private val getSessionUseCase: GetSessionUseCase,
) : BaseViewModel() {

    private val _isWizardCompleted = mutableStateOf(false)
    val isWizardCompleted get() = _isWizardCompleted

    private val _goToHome = mutableStateOf(false)
    val goToHome get() = _goToHome

    init {
        validateShowWizard()
    }

    fun validateShowWizard() {
        viewModelScope.launch {
            validateWizardUseCase.execute().collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    _isWizardCompleted.value = result.showWizard
                    _goToHome.value = !result.showWizard
                }
            }
        }
    }

    fun validateSession() {
        viewModelScope.launch {
            getSessionUseCase.execute().collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    if (result) {
                        _goToHome.value = true
                        _isWizardCompleted.value = false
                    } else {
                        _goToHome.value = false
                        _isWizardCompleted.value = true
                    }
                }
            }
        }
    }
}