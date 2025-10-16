package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.UserDto

class UserRepository(private val db: ParkingDatabase): IUserRepository {
    override suspend fun insertUser(userDto: UserDto) {
        db.userDao.insertUser(userDto)
    }

    override suspend fun getUserByUserName(email: String): UserDto? {
        return db.userDao.getUser(email)
    }
}