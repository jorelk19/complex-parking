package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.CarGuestDto
import java.util.Date

@Dao
interface CarGuestDao {
    @Insert
    suspend fun insertCarGuest(carGuestDto: CarGuestDto)
    @Update
    suspend fun updateCarGuest(carGuestDto: CarGuestDto)

    @Query("SELECT * FROM carGuest WHERE plate = :plate")
    suspend fun getCarGuestByPlate(plate: String): CarGuestDto

    @Query("SELECT * FROM carGuest WHERE date BETWEEN :startDate AND :endDate AND isInComplex == false AND createdBy = :userName")
    suspend fun getCarGuestByDate(startDate: Date, endDate: Date, userName: String): List<CarGuestDto>
    @Query("SELECT * FROM carGuest")
    suspend fun getAllGuest(): List<CarGuestDto>
}