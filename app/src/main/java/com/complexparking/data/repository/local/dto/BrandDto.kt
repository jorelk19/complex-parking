package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.complexparking.entities.Brand

@Entity(tableName = "brand")
data class BrandDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)