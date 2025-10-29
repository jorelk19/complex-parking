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

                }
            }
        }
    }

    override fun onStartScreen() {
        initView()
    }
}