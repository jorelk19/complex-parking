package com.complexparking.entities

import java.util.Date

data class CarGuest(
    val plate: String = "",
    val complexUnit: Int = 0,
    val date: Date = Date(),
    val hourStart: Long = Date().time,
    val hourEnd: Long? = null,
    val isInComplex: Boolean = false,
    val createdBy: String = ""
)
