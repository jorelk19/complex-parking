package com.complexparking.entities

import java.time.LocalTime
import java.util.Date

data class Visitor(
    val plate: String,
    val house: Int,
    val date: Date,
    val hourStart: Long,
    val hourEnd: Long?,
    val isInComplex: Boolean,
)
