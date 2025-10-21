package com.complexparking.domain.useCase

import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.utils.preferences.IS_WIZARD_COMPLETED
import com.complexparking.utils.preferences.StorePreferenceUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SplashScreenGetWizardCompleteUseCase(
    private val storePreferenceUtils: StorePreferenceUtils,
) : BaseUseCase<Any, Boolean> {
    override suspend fun execute(params: Any?): Flow<ResultUseCaseState<Boolean>> = flow {
        val result = storePreferenceUtils.getBoolean(IS_WIZARD_COMPLETED, false)
        emit(ResultUseCaseState.Success(result))
    }
}