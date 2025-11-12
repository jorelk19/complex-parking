package com.complexparking.ui.search

import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.CheckoutGuestCarUseCase
import com.complexparking.ui.base.BaseViewModel
import com.complexparking.ui.utilities.ErrorType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchScreenViewModel(
    private val checkoutGuestCarUseCase: CheckoutGuestCarUseCase,
) : BaseViewModel() {

    private val _searchScreenModel = MutableStateFlow(SearchScreenModel())
    val searchScreenModel get() = _searchScreenModel.asStateFlow()

    private fun initView() {
        _searchScreenModel.value = SearchScreenModel()
    }

    fun onParkingCheckout() {
        viewModelScope.launch {
            checkoutGuestCarUseCase.execute(_searchScreenModel.value.plateText).collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    result?.let { parkingPaymentData ->
                        _searchScreenModel.update {
                            it.copy(
                                plateErrorType = ErrorType.NONE,
                                plateError = false,
                                unitVisited = parkingPaymentData.unitVisited,
                                totalToPay = parkingPaymentData.totalToPay,
                                billedTime = parkingPaymentData.parkingDuration
                            )
                        }
                    } ?: run {
                        _searchScreenModel.update {
                            it.copy(
                                plateErrorType = ErrorType.INVALID_PLATE,
                                plateError = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun onTextPlateChange(plateText: String) {
        if (plateText.isEmpty()) {
            _searchScreenModel.update {
                it.copy(
                    plateText = plateText,
                    plateErrorType = ErrorType.EMPTY_PLATE,
                    plateError = true,
                    isButtonSearchEnabled = false
                )
            }
        } else if (plateText.length < 6) {
            _searchScreenModel.update {
                it.copy(
                    plateErrorType = ErrorType.INVALID_PLATE,
                    plateError = true,
                    plateText = plateText,
                    isButtonSearchEnabled = false
                )
            }
        } else if (plateText.length == 6) {
            _searchScreenModel.update {
                it.copy(
                    plateErrorType = ErrorType.NONE,
                    plateError = false,
                    plateText = plateText,
                )
            }
        }
        validateButton()
    }

    private fun validateButton() {
        val enabled = if (_searchScreenModel.value.plateError ||
            _searchScreenModel.value.plateText.isEmpty()
        ) {
            false
        } else {
            true
        }
        _searchScreenModel.update {
            it.copy(
                isButtonSearchEnabled = enabled
            )
        }
    }

    fun onSearchButtonClick() {
        onParkingCheckout()
    }

    override fun onStartScreen() {
        initView()
    }
}