package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.CarGuestDto

@Dao
interface CarGuestDao {
    @Upsert
    suspend fun insertCarVisitor(carGuestDto: CarGuestDto)
}