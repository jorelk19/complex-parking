package com.complexparking.domain.useCase

import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.utils.preferences.StorePreferenceUtils
import com.complexparking.utils.preferences.USER_DATA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CloseSessionUseCase(
    private val storePreferenceUtils: StorePreferenceUtils
): BaseUseCase<Nothing, Boolean> {
    override suspend fun execute(params: Nothing?): Flow<ResultUseCaseState<Boolean?>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            storePreferenceUtils.putString(USER_DATA, "")
            emit(ResultUseCaseState.Success(true))
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}