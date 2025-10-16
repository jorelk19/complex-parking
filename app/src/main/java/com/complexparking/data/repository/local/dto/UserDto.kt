package com.complexparking.data.repository.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user")
data class UserDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String,
    val password: String,
    val name: String,
    val date: Date,
    val isAdmin: Boolean
)