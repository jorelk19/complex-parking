package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.ICarGuestRepository
import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.ParkingPaymentData
import com.complexparking.utils.extensions.getCurrentTime
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class CheckoutGuestCarUseCase(
    private val carGuestRepository: ICarGuestRepository,
    private val complexRepository: IComplexRepository,
) : BaseUseCase<String, ParkingPaymentData?> {
    override suspend fun execute(params: String?): Flow<ResultUseCaseState<ParkingPaymentData?>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            params?.let {
                val state = withContext(Dispatchers.IO) {
                    val carGuestDto = carGuestRepository.getGuestByPlate(it)
                    val complexConfiguration = complexRepository.getComplexConfiguration()
                    carGuestDto?.let {
                        val currentTime = getCurrentTime()
                        val timeStart = it.hourStart.toDuration(DurationUnit.MILLISECONDS)
                        val timeEnd = currentTime.toDuration(DurationUnit.MILLISECONDS)
                        val elapsedTime = timeEnd - timeStart
                        val totalTime = TimeUnit.MILLISECONDS.toHours(elapsedTime.toLong(DurationUnit.HOURS))
                        val totalToPay = totalTime * complexConfiguration.parkingPrice
                        val newCarGuestDto = it.copy(
                            hourEnd = currentTime,
                            isInComplex = false
                        )
                        carGuestRepository.updateGuest(newCarGuestDto)
                        ParkingPaymentData(
                            totalToPay = totalToPay,
                            parkingDuration = totalTime,
                            unitVisited = it.house
                        )
                    } ?: run {
                        null
                    }
                }
                emit(ResultUseCaseState.Success(state))
            } ?: run {
                emit(ResultUseCaseState.Success(null))
            }
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}


