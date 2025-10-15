package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brand")
data class BrandDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)