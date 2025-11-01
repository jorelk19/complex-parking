package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IParkingConfigurationRepository
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.ParkingConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetParkingConfigurationUseCase(
    private val parkingConfigurationRepository: IParkingConfigurationRepository
): BaseUseCase<Nothing, ParkingConfiguration> {
    override suspend fun execute(params: Nothing?): Flow<ResultUseCaseState<ParkingConfiguration?>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            val result = withContext(Dispatchers.IO) { parkingConfigurationRepository.getParkingConfiguration() }
            emit(ResultUseCaseState.Success(result))
        }catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }

}