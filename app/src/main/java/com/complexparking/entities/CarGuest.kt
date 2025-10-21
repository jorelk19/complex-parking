package com.complexparking.entities

import java.util.Date

data class CarGuest(
    val plate: String,
    val house: Int,
    val date: Date,
    val hourStart: Long,
    val hourEnd: Long?,
    val isInComplex: Boolean,
)
