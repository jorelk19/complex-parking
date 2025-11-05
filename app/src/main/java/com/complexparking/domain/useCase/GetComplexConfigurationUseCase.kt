package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.data.repository.local.mappers.toComplexConfiguration
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.ComplexConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetComplexConfigurationUseCase(
    private val complexConfigurationRepository: IComplexRepository,
) : BaseUseCase<Nothing, ComplexConfiguration> {
    override suspend fun execute(params: Nothing?): Flow<ResultUseCaseState<ComplexConfiguration?>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            val result = withContext(Dispatchers.IO) {
                complexConfigurationRepository.getComplexConfiguration()
            }
            emit(ResultUseCaseState.Success(result.toComplexConfiguration()))
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}