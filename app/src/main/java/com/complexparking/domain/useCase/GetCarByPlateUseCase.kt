package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.ICarRepository
import com.complexparking.data.repository.local.mappers.toCar
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.Car
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetCarByPlateUseCase(
    private val carRepository: ICarRepository,
) : BaseUseCase<String, Car?> {
    override suspend fun execute(params: String?): Flow<ResultUseCaseState<Car?>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            params?.let {
                val carDto = withContext(Dispatchers.IO) { carRepository.getCarByPlate(params) }
                emit(ResultUseCaseState.Success(carDto?.toCar()))
            } ?: run {
                emit(ResultUseCaseState.Success(null))
            }
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}


