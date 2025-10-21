package com.complexparking.domain.useCase

import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.utils.preferences.IS_WIZARD_COMPLETED
import com.complexparking.utils.preferences.StorePreferenceUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SplashScreenSetWizardCompleteUseCase(
    private val storePreferenceUtils: StorePreferenceUtils,
) : BaseUseCase<Any, Any?> {
    override suspend fun execute(params: Any?): Flow<ResultUseCaseState<Any?>> = flow {
        storePreferenceUtils.putBoolean(IS_WIZARD_COMPLETED, true)
        emit(ResultUseCaseState.Success(null))
    }
}