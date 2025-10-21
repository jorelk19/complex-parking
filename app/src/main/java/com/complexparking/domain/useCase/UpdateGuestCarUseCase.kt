package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.ICarGuestRepository
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import java.util.Calendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateGuestCarUseCase(
    private val guestRepository: ICarGuestRepository,
) : BaseUseCase<String, Any?> {
    override suspend fun execute(params: String?): Flow<ResultUseCaseState<Any?>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            params?.let {
                val guest = guestRepository.getGuestByPlate(params.toString())
                guest?.let {
                    guestRepository.updateGuest(
                        guest = guest.copy(
                            isInComplex = false,
                            hourEnd = Calendar.getInstance().timeInMillis
                        )
                    )
                }
                emit(ResultUseCaseState.Success(null))
            }
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}