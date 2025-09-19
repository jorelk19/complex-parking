package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val lastName: String,
    val document: String,
    val documentType: DocumentTypeDto,
    val house: Int,
    val cellNumber: String
)