package com.complexparking.ui.home

import com.complexparking.ui.utilities.ErrorType

data class HomeScreenModel(
    val onClickHeaderBack: () -> Unit = {},
    val onTextPlateChange: (String) -> Unit = {},
    val onTextUnitChange: (String) -> Unit = {},
    val onRegisterButtonClick: () -> Unit = {},
    val plateText: String = "",
    val hourArrive: String = "",
    val date: String = "",
    val unit: String = "",
    val unitError: Boolean = false,
    val plateError: Boolean = false,
    val plateFocus: Boolean = false,
    val plateErrorType: ErrorType = ErrorType.NONE,
    val unitErrorType: ErrorType = ErrorType.NONE,
    val isButtonRegisterEnabled: Boolean = false
)