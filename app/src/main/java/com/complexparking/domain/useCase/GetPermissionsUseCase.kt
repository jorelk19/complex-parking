package com.complexparking.domain.useCase

import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.utils.preferences.ARE_PERMISSIONS_GRANTED
import com.complexparking.utils.preferences.StorePreferenceUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPermissionsUseCase(
    private val storePreferenceUtils: StorePreferenceUtils
): BaseUseCase<Nothing, Boolean> {
    override suspend fun execute(params: Nothing?): Flow<ResultUseCaseState<Boolean?>> = flow {
        try {
            val permissionsGranted =  storePreferenceUtils.getBoolean(ARE_PERMISSIONS_GRANTED, false)
            emit(ResultUseCaseState.Success(permissionsGranted))
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}