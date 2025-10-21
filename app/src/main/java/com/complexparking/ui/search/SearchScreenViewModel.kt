package com.complexparking.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.R
import com.complexparking.domain.useCase.GetCarByPlateUseCase
import com.complexparking.ui.base.BaseViewModel
import com.complexparking.ui.controls.SnackBarController
import com.complexparking.ui.controls.SnackBarEvents
import com.complexparking.ui.controls.SnackType
import kotlinx.coroutines.launch

class SearchScreenViewModel(
    private val getCarByPlateUseCase: GetCarByPlateUseCase,
) : BaseViewModel() {

    private val _searchScreenModel = mutableStateOf(SearchScreenModel())
    val searchScreenModel get() = _searchScreenModel

    init {
        initView()
    }

    private fun initView() {
        _searchScreenModel.value = SearchScreenModel()
    }

    fun onPlateChange(plate: String) {
        _searchScreenModel.value = _searchScreenModel.value.copy(
            plateText = plate
        )
    }

    fun onParkingCheckout() {
        viewModelScope.launch {
            getCarByPlateUseCase.execute(_searchScreenModel.value.plateText).collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    result?.let {
                        SnackBarController.sendEvent(
                            event = SnackBarEvents(
                                titleId = R.string.wizard_user_creation_user_repeat_password,
                                messageId = R.string.wizard_user_creation_user_repeat_password,
                                iconId = R.drawable.ic_close,
                                buttonIconId = R.drawable.ic_close,
                                snackType = SnackType.INFO
                            )
                        )
                    } ?: run {
                        /*Car doesn't exist message*/
                    }
                }
            }
        }
    }
}