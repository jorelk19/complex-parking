package com.complexparking.ui.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.domain.useCase.LoadSeedDataUseCase
import com.complexparking.domain.useCase.ValidateWizardUseCase
import com.complexparking.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val validateWizardUseCase: ValidateWizardUseCase,
    private val loadSeedDataUseCase: LoadSeedDataUseCase
) : BaseViewModel() {

    private val _splashScreenState = MutableStateFlow(ResultUseCaseState.Initial)
    val splashScreenState = _splashScreenState
        .onStart {
            initializeDataBase()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _splashScreenState
        )

    private fun initializeDataBase() {
        viewModelScope.launch {
            loadSeedDataUseCase.execute().collect { resultUseCase ->
                validateUseCaseResult(resultUseCase) { result ->
                    validateShowWizard()
                }
            }
        }
    }

    private val _isWizardCompleted = mutableStateOf(false)
    val isWizardCompleted get() = _isWizardCompleted

    private val _goToHome = mutableStateOf(false)
    val goToHome get() = _goToHome

    /*init {
        validateShowWizard()
    }*/

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
}