package com.complexparking.entities

data class CashClosingCar(
    val plate: String = "",
    val totalToPayByCar: Double = 0.0,
    val hoursRegistered: Double = 0.0
)