package com.complexparking.entities

import java.util.Date

data class CarGuest(
    val plate: String = "",
    val house: Int = 0,
    val date: Date = Date(),
    val hourStart: Long = 0L,
    val hourEnd: Long? = null,
    val isInComplex: Boolean = false,
)
