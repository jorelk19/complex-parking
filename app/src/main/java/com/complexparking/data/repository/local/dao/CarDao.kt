package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.CarDto

@Dao
interface CarDao {
    @Upsert
    fun insertCar(cartDto: CarDto)

    @Query("SELECT * FROM car WHERE plate = :plate")
    fun getCarByPlate(plate: String): CarDto?
}