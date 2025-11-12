package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetValidUnitsUseCase(
    private val complexRepository: IComplexRepository
): BaseUseCase<Any?, Int> {
    override suspend fun execute(params: Any?): Flow<ResultUseCaseState<Int>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            val currentUnits = withContext(Dispatchers.IO) { complexRepository.getComplexUnitQuantity() }
            /*val result = params in 1..currentUnits*/
            emit(ResultUseCaseState.Success(currentUnits))
        }catch (ex: Exception){
            emit(ResultUseCaseState.Error(ex))
        }
    }
}