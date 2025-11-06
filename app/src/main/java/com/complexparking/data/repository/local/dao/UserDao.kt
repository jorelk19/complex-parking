package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import com.complexparking.data.repository.local.dto.UserDto

@Dao
interface UserDao {
    @Insert(onConflict = ABORT)
    suspend fun insertUser(userDto: UserDto)
    @Query("SELECT * FROM user WHERE userName = :email")
    suspend fun getUser(email: String): UserDto?

    @Update
    suspend fun updateUserName(userDto: UserDto)
}