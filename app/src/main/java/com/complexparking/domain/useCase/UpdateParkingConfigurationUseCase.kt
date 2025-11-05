package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.ComplexConfiguration
import com.complexparking.entities.toComplexDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UpdateParkingConfigurationUseCase(
    private val complexRepository: IComplexRepository,
) : BaseUseCase<ComplexConfiguration, Boolean> {
    override suspend fun execute(params: ComplexConfiguration?): Flow<ResultUseCaseState<Boolean>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            params?.let { configuration ->
                withContext(Dispatchers.IO) {
                    val complexConfiguration = complexRepository.getComplexConfiguration()
                    val newConfiguration = configuration.copy(id = complexConfiguration.id)
                    complexRepository.updateComplexConfiguration(complexDto = newConfiguration.toComplexDto())

                }
                emit(ResultUseCaseState.Success(true))
            }
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}