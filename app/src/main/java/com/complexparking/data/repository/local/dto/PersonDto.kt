package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class PersonDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val lastName: String,
    val document: String,
    val documentType: DocumentTypeDto,
    val house: Int,
    val cellNumber: String
)