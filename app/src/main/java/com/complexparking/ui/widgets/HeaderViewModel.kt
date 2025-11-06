package com.complexparking.ui.widgets

import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.GetUserDataUseCase
import com.complexparking.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HeaderViewModel(
    private val getUserDataUseCase: GetUserDataUseCase
) : BaseViewModel() {
    private val _headerState = MutableStateFlow(HeaderState())
    val headerState = _headerState.asStateFlow()

    override fun onStartScreen() {
        loadProfileData()
    }

    fun loadProfileData() {
        viewModelScope.launch {
            getUserDataUseCase.execute().collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    result?.let {
                        _headerState.update {
                            it.copy(
                                userData = result
                            )
                        }
                    }
                }
            }
        }
    }
}