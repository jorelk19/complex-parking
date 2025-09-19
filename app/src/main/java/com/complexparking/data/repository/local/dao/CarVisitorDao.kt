package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.CarVisitorDto

@Dao
interface CarVisitorDao {
    @Upsert
    suspend fun insertCarVisitor(carVisitorDao: CarVisitorDto)
}