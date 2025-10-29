package com.complexparking.ui.search

import com.complexparking.ui.utilities.ErrorType

data class SearchScreenModel(
    val plateText: String = "",
    val plateError: Boolean = false,
    val plateFocus: Boolean = false,
    val plateErrorType: ErrorType = ErrorType.NONE,
    val onTextPlateChange: (String) -> Unit = {},
    val hourArrival: String = "",
    val hourDeparture: String = "",
    val date: String = "",
    val isButtonSearchEnabled: Boolean = false,
    val onSearchButtonClick: () -> Unit = {}
)
