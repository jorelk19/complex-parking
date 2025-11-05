package com.complexparking.ui.splash

import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.LoadSeedDataUseCase
import com.complexparking.domain.useCase.ValidateWizardUseCase
import com.complexparking.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val validateWizardUseCase: ValidateWizardUseCase,
    private val loadSeedDataUseCase: LoadSeedDataUseCase,
) : BaseViewModel() {
    private fun initializeDataBase() {
        viewModelScope.launch {
            loadSeedDataUseCase.execute().collect { resultUseCase ->
                validateUseCaseResult(resultUseCase) { result ->
                    validateShowWizard()
                }
            }
        }
    }

    private val _splashScreenState = MutableStateFlow(SplashScreenState())
    val splashScreenState get() = _splashScreenState.asStateFlow()

    suspend fun validateShowWizard() {
        //viewModelScope.launch {
            validateWizardUseCase.execute().collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    _splashScreenState.update {
                        it.copy(
                            isWizardCompleted = result.isWizardCompleted,
                            goToHome = result.userHasSession
                        )
                    }
                }
            }
        //}
    }

    override fun onStartScreen() {
        initializeDataBase()
    }
}