package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.mappers.toParkingConfiguration
import com.complexparking.entities.ParkingConfiguration

class ParkingConfigurationRepository(
    private val db: ParkingDatabase,
) : IParkingConfigurationRepository {
    override suspend fun getParkingConfiguration(): ParkingConfiguration {
        val parkingConfigurationDtoList = db.parkingConfigurationDao.getParkingConfiguration()
        return parkingConfigurationDtoList.first().toParkingConfiguration()
    }

    override suspend fun updateParkingConfiguration(parkingConfiguration: ParkingConfiguration) {
        db.parkingConfigurationDao.updateParkingConfiguration(
            currentId = parkingConfiguration.id,
            newParkingPrice = parkingConfiguration.parkingPrice,
            newMaxHourFree = parkingConfiguration.maxFreeHour
        )
    }
}