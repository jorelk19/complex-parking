package com.complexparking.domain.useCase

import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.utils.preferences.IS_WIZARD_COMPLETED
import com.complexparking.utils.preferences.StorePreferenceUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SetWizardUseCase(
    private val storePreferenceUtils: StorePreferenceUtils
): BaseUseCase<Any, Boolean>{
    override suspend fun execute(params: Any?): Flow<ResultUseCaseState<Boolean>> = flow {
        try {
            storePreferenceUtils.putBoolean(IS_WIZARD_COMPLETED, true)
            emit(ResultUseCaseState.Success(true))
        }catch(ex: Exception){
            emit(ResultUseCaseState.Error(ex))
        }
    }
}