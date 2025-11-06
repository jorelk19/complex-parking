package com.complexparking.entities

import java.util.Date

data class UserData(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String ="",
    val creationDate: Date = Date(),
    val isAdmin: Boolean = false
)
