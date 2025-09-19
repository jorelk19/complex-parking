package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CarDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val color: String,
    val brand: BrandDto,
    val model: Int
)