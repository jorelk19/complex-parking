package com.complexparking.entities

data class ComplexConfiguration(
    val id: Int,
    val parkingPrice: Double,
    val maxFreeHour: Int,
    val adminName: String,
    val complexAddress: String,
    val complexUnits: Int,
    val complexQuantityParking: Int,
    val complexName: String
)
