package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BrandDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)