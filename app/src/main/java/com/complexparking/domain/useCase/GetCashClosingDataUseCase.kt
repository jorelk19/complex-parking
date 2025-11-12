package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.ICarGuestRepository
import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.data.repository.local.dto.UserDto
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.CashClosingCar
import com.complexparking.entities.CashClosingData
import com.complexparking.ui.utilities.fromJson
import com.complexparking.utils.preferences.StorePreferenceUtils
import com.complexparking.utils.preferences.USER_DATA
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class GetCashClosingDataUseCase(
    private val carGuestRepository: ICarGuestRepository,
    private val complexRepository: IComplexRepository,
    private val storePreferenceUtils: StorePreferenceUtils,
) : BaseUseCase<Any, CashClosingData> {
    @OptIn(ExperimentalTime::class)
    override suspend fun execute(params: Any?): Flow<ResultUseCaseState<CashClosingData?>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            // Get a Calendar instance representing the current date and time
            val currentTime = LocalTime.now()
            val currentHour = currentTime.hour
            val calendar = Calendar.getInstance()
            var dateStart: Date
            var dateEnd: Date
            if (currentHour >= 6 && currentHour <= 18) {
                //Day turn
                // Reset the time components
                calendar.set(Calendar.HOUR_OF_DAY, 6)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                dateStart = calendar.time
                calendar.set(Calendar.HOUR_OF_DAY, 17)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
                dateEnd = calendar.time
            } else {
                //Night turn
                // Reset the time components
                calendar.set(Calendar.HOUR_OF_DAY, 18)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                dateStart = calendar.time
                calendar.add(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 5)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
                dateEnd = calendar.time
            }

            val complexConfiguration = complexRepository.getComplexConfiguration()
            val data = storePreferenceUtils.getString(USER_DATA, "")
            data?.let {
                val userData = data.fromJson<UserDto>()
                val carGuestList = carGuestRepository.getGuestByDate(dateStart, dateEnd, userData.userName)
                val carlist = carGuestRepository.getAllGuest()
                val cashClosingCarList = arrayListOf<CashClosingCar>()
                carGuestList.forEach { carGuest ->
                    carGuest.hourEnd?.let {
                        val start = Instant.fromEpochMilliseconds(carGuest.hourStart)
                        val end = Instant.fromEpochMilliseconds(carGuest.hourEnd)
                        val hourToPay = end - start
                        val cashClosingCar = CashClosingCar(
                            totalToPayByCar = hourToPay.toDouble(DurationUnit.HOURS) * complexConfiguration.parkingPrice,
                            plate = carGuest.plate,
                            hoursRegistered = hourToPay.toDouble(DurationUnit.HOURS)
                        )
                        cashClosingCarList.add(cashClosingCar)
                    } ?: run {
                        throw Exception("End hour null")
                    }
                }
                val cashClosingData = CashClosingData(
                    cashClosingCarList = cashClosingCarList,
                    totalCash = cashClosingCarList.sumOf { it.totalToPayByCar },
                    quantityCars = cashClosingCarList.count()
                )
                emit(ResultUseCaseState.Success(cashClosingData))
            } ?: run {
                throw Exception("User data is null")
            }
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}