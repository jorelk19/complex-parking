package com.complexparking.ui.cashClosing

import java.util.Date

data class CashClosingState(
    val closingDate: Date = Date(),
    val userId: Int = 0,
    val totalCash: Double = 0.0,
    val quantityCars: Int = 0,
    val hourValue: Double = 0.0,
    val startingCash: Double = 100.00, // Example value, could be fetched
    val totalSales: Double = 1550.75,   // Example value, could be fetched
    val cashPayments: Double = 875.50,  // Example value, could be fetched
    val actualCashCounted: String = "", // User input is a string
    val notes: String = ""
) {
    // Calculated property for expected cash
    val expectedCash: Double
        get() = startingCash + cashPayments

    // Calculated property for the difference
    val difference: Double
        get() {
            val counted = actualCashCounted.toDoubleOrNull() ?: 0.0
            return counted - expectedCash
        }
}
