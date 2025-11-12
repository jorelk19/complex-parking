package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.ICarGuestRepository
import com.complexparking.data.repository.local.ICarRepository
import com.complexparking.data.repository.local.dto.UserDto
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.CarGuest
import com.complexparking.entities.toGuestDto
import com.complexparking.ui.utilities.fromJson
import com.complexparking.ui.utilities.json
import com.complexparking.utils.extensions.getCurrentDate
import com.complexparking.utils.extensions.getCurrentTime
import com.complexparking.utils.preferences.StorePreferenceUtils
import com.complexparking.utils.preferences.USER_DATA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CreateGuestUseCase(
    private val carGuestRepository: ICarGuestRepository,
    private val carRepository: ICarRepository,
    private val storePreferenceUtils: StorePreferenceUtils
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
                        val data = storePreferenceUtils.getString(USER_DATA, "")
                        data?.let {
                            val userData = data.fromJson<UserDto>()
                            val newGuest = params.copy(
                                hourStart = getCurrentTime(),
                                date = getCurrentDate(),
                                createdBy = userData.userName
                            )
                            carGuestRepository.createGuest(newGuest.toGuestDto())
                            result = ResultUseCaseState.Success(true)
                        } ?: run {
                            throw Exception("User data is null")
                        }
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