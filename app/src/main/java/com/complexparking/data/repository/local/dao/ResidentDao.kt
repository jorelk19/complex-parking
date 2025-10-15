package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.ResidentDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ResidentDao {
    @Upsert
    suspend fun insertResident(residentDto: ResidentDto)
    @Delete
    suspend fun deleteResident(residentDto: ResidentDto)

    @Query("SELECT * FROM resident WHERE id = :residentId")
    suspend fun getResident(residentId: Int): ResidentDto

    @Query("SELECT * FROM resident ORDER BY personDto ASC")
    suspend fun getResidentOrderByHouse(): List<ResidentDto>
}