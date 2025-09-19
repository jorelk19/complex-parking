package com.complexparking.domain.useCase

import com.complexparking.domain.interfaces.ISplashScreenUseCase
import com.complexparking.utils.preferences.IS_WIZARD_COMPLETED
import com.complexparking.utils.preferences.StorePreferenceUtils

class SplashScreenUseCase(private val storePreferenceUtils: StorePreferenceUtils): ISplashScreenUseCase {
    override suspend fun isWizardComplete(): Boolean {
        return storePreferenceUtils.getBoolean(IS_WIZARD_COMPLETED, false)
    }

    override suspend fun wizardComplete() {
        storePreferenceUtils.putBoolean(IS_WIZARD_COMPLETED, true)
    }
}