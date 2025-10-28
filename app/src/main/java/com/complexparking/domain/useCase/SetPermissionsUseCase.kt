package com.complexparking.domain.useCase

import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.utils.preferences.ARE_PERMISSIONS_GRANTED
import com.complexparking.utils.preferences.StorePreferenceUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SetPermissionsUseCase(
    private val storePreferenceUtils: StorePreferenceUtils
): BaseUseCase<Boolean, Nothing>{
    override suspend fun execute(params: Boolean?): Flow<ResultUseCaseState<Nothing?>> = flow {
        try {
            params?.let {
                storePreferenceUtils.putBoolean(ARE_PERMISSIONS_GRANTED, it)
            }
            emit(ResultUseCaseState.Success(null))
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}