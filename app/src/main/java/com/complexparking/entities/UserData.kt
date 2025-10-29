package com.complexparking.entities

import java.util.Date

data class UserData(
    val name: String = "",
    val email: String = "",
    val password: String ="",
    val creationDate: Date = Date(),
    val isAdmin: Boolean = false
)
