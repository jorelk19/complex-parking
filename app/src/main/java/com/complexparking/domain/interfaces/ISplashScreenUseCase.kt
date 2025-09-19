package com.complexparking.domain.interfaces

interface ISplashScreenUseCase {
    suspend fun isWizardComplete(): Boolean
    suspend fun wizardComplete()
}