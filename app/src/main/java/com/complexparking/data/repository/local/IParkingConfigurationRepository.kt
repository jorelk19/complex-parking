package com.complexparking.data.repository.local

import com.complexparking.entities.ParkingConfiguration

interface IParkingConfigurationRepository {
    suspend fun getParkingConfiguration(): ParkingConfiguration
    suspend fun updateParkingConfiguration(parkingConfiguration: ParkingConfiguration)
}