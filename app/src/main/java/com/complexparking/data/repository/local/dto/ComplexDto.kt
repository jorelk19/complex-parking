package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "complex")
data class ComplexDto (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val complexName: String,
    val complexUnits: Int,
    val complexAddress: String,
    val quantityParking: Int
)