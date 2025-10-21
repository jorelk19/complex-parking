package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.complexparking.entities.Brand
import com.complexparking.entities.Car

@Entity(tableName = "car")
data class CarDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val color: String,
    val brand: BrandDto,
    val plate: String,
    val unit: Int
)


