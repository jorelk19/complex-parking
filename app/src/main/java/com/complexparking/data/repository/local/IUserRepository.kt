package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.UserDto

interface IUserRepository {
    suspend fun insertUser(userDto: UserDto)
    suspend fun getUserByUserName(email: String): UserDto?
}