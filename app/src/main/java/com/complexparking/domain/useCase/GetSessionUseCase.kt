package com.complexparking.domain.useCase

import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.utils.preferences.StorePreferenceUtils
import com.complexparking.utils.preferences.USER_DATA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSessionUseCase(
    private val storePreferenceUtils: StorePreferenceUtils
): BaseUseCase<Any, Boolean> {
    override suspend fun execute(params: Any?): Flow<ResultUseCaseState<Boolean>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            val data = storePreferenceUtils.getString(key = USER_DATA, "")
            emit(ResultUseCaseState.Success(!data.isNullOrEmpty()))
        }catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}