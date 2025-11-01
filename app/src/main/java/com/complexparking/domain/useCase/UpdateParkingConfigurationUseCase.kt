package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IParkingConfigurationRepository
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.ParkingConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UpdateParkingConfigurationUseCase(
    private val parkingConfigurationRepository: IParkingConfigurationRepository
): BaseUseCase<ParkingConfiguration, Boolean> {
    override suspend fun execute(params: ParkingConfiguration?): Flow<ResultUseCaseState<Boolean>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            params?.let { parkingConfiguration ->
                withContext(Dispatchers.IO) { parkingConfigurationRepository.updateParkingConfiguration(parkingConfiguration = parkingConfiguration) }
                emit(ResultUseCaseState.Success(true))
            }
        }catch(ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}