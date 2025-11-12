package com.complexparking.ui.search

import com.complexparking.ui.utilities.ErrorType

data class SearchScreenModel(
    val plateText: String = "",
    val plateError: Boolean = false,
    val plateFocus: Boolean = false,
    val plateErrorType: ErrorType = ErrorType.NONE,
    val hourArrival: String = "",
    val hourDeparture: String = "",
    val date: String = "",
    val totalToPay: Double = 0.0,
    val unitVisited: Int = 0,
    val billedTime: Long = 0L,
    val isButtonSearchEnabled: Boolean = false
)
