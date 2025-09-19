package com.complexparking.ui.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.interfaces.ISplashScreenUseCase
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val iSplashScreenUseCase: ISplashScreenUseCase,
) : ViewModel() {

    private val _isWizardCompleted = mutableStateOf(false)
    val isWizardCompleted = _isWizardCompleted

    init {
        validateShowWizard()
    }

    fun validateShowWizard() {
        viewModelScope.launch {
            runCatching {
                iSplashScreenUseCase.isWizardComplete()
            }.onSuccess {
                isWizardCompleted.value = it
            }
        }
    }
}