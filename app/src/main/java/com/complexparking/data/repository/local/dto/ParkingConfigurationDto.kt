package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parkingConfiguration")
data class ParkingConfigurationDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val parkingPrice: Double,
    val maxFreeHour: Int
)
