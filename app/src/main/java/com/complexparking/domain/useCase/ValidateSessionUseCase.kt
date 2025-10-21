package com.complexparking.domain.useCase

import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.utils.preferences.StorePreferenceUtils
import com.complexparking.utils.preferences.USER_DATA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ValidateSessionUseCase(
    private val storePreferenceUtils: StorePreferenceUtils,
) : BaseUseCase<Any, Boolean> {
    override suspend fun execute(params: Any?): Flow<ResultUseCaseState<Boolean>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            emit(ResultUseCaseState.Success(storePreferenceUtils.getBoolean(USER_DATA, false)))
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}