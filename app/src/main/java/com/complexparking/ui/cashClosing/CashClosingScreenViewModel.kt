package com.complexparking.ui.cashClosing

import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.GetCashClosingDataUseCase
import com.complexparking.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CashClosingScreenViewModel(
    private val getCashClosingDataUseCase: GetCashClosingDataUseCase
) : BaseViewModel() {

    private val _cashClosingState = MutableStateFlow(CashClosingState())
    val cashClosingState get() = _cashClosingState.asStateFlow()

    override fun onStartScreen() {
        viewModelScope.launch {
            getCashClosingDataUseCase.execute().collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    val res = result
                }
            }
        }
    }

    fun onCashClosingAction() {

    }
}