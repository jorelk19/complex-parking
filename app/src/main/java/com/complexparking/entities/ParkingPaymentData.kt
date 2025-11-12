package com.complexparking.entities

data class ParkingPaymentData(
    val totalToPay: Double,
    val parkingDuration: Long,
    val unitVisited: Int
)
