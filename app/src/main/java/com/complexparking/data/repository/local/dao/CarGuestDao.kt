package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.CarGuestDto

@Dao
interface CarGuestDao {
    @Insert
    suspend fun insertCarGuest(carGuestDto: CarGuestDto)
}