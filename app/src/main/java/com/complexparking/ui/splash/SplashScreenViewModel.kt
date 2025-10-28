package com.complexparking.ui.splash

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
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
    private val loadSeedDataUseCase: LoadSeedDataUseCase,
) : BaseViewModel() {
    /*private val _isCompletedLoadingData = MutableStateFlow(false)
    val isCompletedLoadingData = _isCompletedLoadingData
        .onStart {
            initializeDataBase()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )*/

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
                    _isWizardCompleted.value = result.isWizardCompleted
                    _goToHome.value = result.userHasSession
                }
            }
        }
    }

    override fun onStartScreen() {
        initializeDataBase()
    }
}