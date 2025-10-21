package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.CarGuestDto

@Dao
interface CarGuestDao {
    @Insert
    suspend fun insertCarGuest(carGuestDto: CarGuestDto)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCarGuest(carGuestDto: CarGuestDto)

    @Query("SELECT * FROM carGuest WHERE plate = :plate")
    suspend fun getCarGuestByPlate(plate: String): CarGuestDto
}