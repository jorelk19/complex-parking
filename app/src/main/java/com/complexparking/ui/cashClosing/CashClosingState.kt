package com.complexparking.ui.cashClosing

import java.util.Date

data class CashClosingState(
    val closingDate: Date = Date(),
    val userId: Int = 0,
    val totalCash: Double = 0.0,
    val quantityCars: Int = 0,
    val hourValue: Double = 0.0
)
