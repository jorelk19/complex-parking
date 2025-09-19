package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class CarVisitorDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val plate: String,
    val house: Int,
    val date: Date,
    val hourStart: Long,
    val hourEnd: Long?,
    val isInComplex: Boolean,
)
