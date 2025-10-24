package com.complexparking.domain.useCase

import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.WizardState
import com.complexparking.utils.preferences.IS_WIZARD_COMPLETED
import com.complexparking.utils.preferences.StorePreferenceUtils
import com.complexparking.utils.preferences.USER_DATA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ValidateWizardUseCase(
    private val storePreferenceUtils: StorePreferenceUtils,
) : BaseUseCase<Any, WizardState> {
    override suspend fun execute(params: Any?): Flow<ResultUseCaseState<WizardState>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            val isCompleted = storePreferenceUtils.getBoolean(IS_WIZARD_COMPLETED, false)
            val wizardState = if (isCompleted) {
                val hasSession = storePreferenceUtils.getString(USER_DATA, "").toString().isNotEmpty()
                if (hasSession) {
                    WizardState(
                        isWizardCompleted = true,
                        userHasSession = true
                    )
                } else {
                    WizardState(
                        isWizardCompleted = true,
                        userHasSession = false
                    )
                }
            } else {
                WizardState(
                    isWizardCompleted = false,
                    userHasSession = false
                )
            }
            emit(ResultUseCaseState.Success(wizardState))
        }catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }

}