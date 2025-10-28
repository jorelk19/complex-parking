package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.ICarGuestRepository
import com.complexparking.data.repository.local.ICarRepository
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.CarGuest
import com.complexparking.entities.guestToDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CreateGuestUseCase(
    private val carGuestRepository: ICarGuestRepository,
    private val carRepository: ICarRepository,
) : BaseUseCase<CarGuest, Boolean> {
    override suspend fun execute(params: CarGuest?): Flow<ResultUseCaseState<Boolean>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            var result = ResultUseCaseState.Success(false)
            withContext(Dispatchers.IO) {
                params?.let {
                    val carData = carRepository.getCarByPlate(params.plate)
                    if (carData != null) {
                        result = ResultUseCaseState.Success(false)
                    } else {
                        carGuestRepository.createGuest(params.guestToDto())
                        result = ResultUseCaseState.Success(true)
                    }
                } ?: run {
                    result = ResultUseCaseState.Success(false)
                }
            }
            emit(result)
        } catch (exception: Exception) {
            emit(ResultUseCaseState.Error(exception))
        }
    }
}