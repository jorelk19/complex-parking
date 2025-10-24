package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class ValidateUnitUseCase(
    private val complexRepository: IComplexRepository
): BaseUseCase<Int, Boolean> {
    override suspend fun execute(params: Int?): Flow<ResultUseCaseState<Boolean>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            params?.let {
                val currentUnits = withContext(Dispatchers.IO) { complexRepository.getComplexUnitQuantity() }
                val result = params in 1..currentUnits
                emit(ResultUseCaseState.Success(result))
            } ?: run {
                emit(ResultUseCaseState.Success(false))
            }
        }catch (ex: Exception){
            emit(ResultUseCaseState.Error(ex))
        }
    }
}