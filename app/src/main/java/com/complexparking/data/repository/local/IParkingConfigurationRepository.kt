package com.complexparking.data.repository.local

import com.complexparking.entities.ComplexConfiguration

interface IParkingConfigurationRepository {
    suspend fun getParkingConfiguration(): ComplexConfiguration
    suspend fun updateParkingConfiguration(complexConfiguration: ComplexConfiguration)
}