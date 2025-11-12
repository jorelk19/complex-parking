package com.complexparking.entities

data class PrinterStatus(
    val pairedDeviceAddress: String = "",
    val isPaired: Boolean = false
)
