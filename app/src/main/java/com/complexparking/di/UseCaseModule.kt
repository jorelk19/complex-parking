package com.complexparking.di

import com.complexparking.domain.useCase.CreateGuestUseCase
import com.complexparking.domain.useCase.GetCarByPlateUseCase
import com.complexparking.domain.useCase.GetSessionUseCase
import com.complexparking.domain.useCase.LoadComplexUnitDataUseCase
import com.complexparking.domain.useCase.LoginUseCase
import com.complexparking.domain.useCase.SetWizardUseCase
import com.complexparking.domain.useCase.SplashScreenGetWizardCompleteUseCase
import com.complexparking.domain.useCase.SplashScreenSetWizardCompleteUseCase
import com.complexparking.domain.useCase.UpdateGuestCarUseCase
import com.complexparking.domain.useCase.ValidateSessionUseCase
import com.complexparking.domain.useCase.ValidateUnitUseCase
import com.complexparking.domain.useCase.ValidateWizardUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { CreateGuestUseCase(get(), get()) }
    single { LoginUseCase(get(), get()) }
    single { ValidateUnitUseCase(get()) }
    single { SplashScreenSetWizardCompleteUseCase(get()) }
    single { SplashScreenGetWizardCompleteUseCase(get()) }
    single { LoadComplexUnitDataUseCase(get(), get(), get()) }
    single { GetCarByPlateUseCase(get()) }
    single { UpdateGuestCarUseCase(get()) }
    single { GetSessionUseCase(get()) }
    single { LoadComplexUnitDataUseCase(get(), get(), get()) }
    single { SetWizardUseCase(get()) }
    single { ValidateSessionUseCase(get()) }
    single { ValidateWizardUseCase(get()) }
}