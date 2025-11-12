package com.complexparking.entities

data class CashClosingData(
    val cashClosingCarList: List<CashClosingCar>,
    val totalCash: Double = 0.0,
    val quantityCars: Int = 0,
   /* val pendingCash: Double = 0.0,
    val quantityCarsPending: Int = 0*/
)
