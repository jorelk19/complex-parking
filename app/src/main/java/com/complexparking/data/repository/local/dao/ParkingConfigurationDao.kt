package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.ParkingConfigurationDto

@Dao
interface ParkingConfigurationDao {
    @Upsert
    fun insertParkingConfiguration(parkingConfigurationDto: ParkingConfigurationDto)

    @Query("UPDATE parkingConfiguration SET parkingPrice = :newParkingPrice, maxFreeHour = :newMaxHourFree WHERE id = :currentId ")
    fun updateParkingConfiguration(
        currentId: Int,
        newParkingPrice: Double,
        newMaxHourFree: Int
    )

    @Query("SELECT * FROM parkingConfiguration")
    fun getParkingConfiguration(): List<ParkingConfigurationDto>
}