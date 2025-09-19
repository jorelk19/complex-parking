package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResidentDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val personDto: PersonDto,
    val carDto: CarDto
)