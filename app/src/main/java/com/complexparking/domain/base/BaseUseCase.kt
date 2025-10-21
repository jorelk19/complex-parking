package com.complexparking.domain.base

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<T, S> {
    suspend fun execute(params: T? = null): Flow<ResultUseCaseState<S?>>
}