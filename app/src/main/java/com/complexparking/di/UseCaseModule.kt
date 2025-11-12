package com.complexparking.di

import com.complexparking.domain.useCase.CloseSessionUseCase
import com.complexparking.domain.useCase.CreateGuestUseCase
import com.complexparking.domain.useCase.CreateUserUseCase
import com.complexparking.domain.useCase.CheckoutGuestCarUseCase
import com.complexparking.domain.useCase.GetCashClosingDataUseCase
import com.complexparking.domain.useCase.GetComplexConfigurationUseCase
import com.complexparking.domain.useCase.GetPermissionsUseCase
import com.complexparking.domain.useCase.GetPrinterStatusUseCase
import com.complexparking.domain.useCase.GetSessionUseCase
import com.complexparking.domain.useCase.LoadComplexUnitDataUseCase
import com.complexparking.domain.useCase.LoadSeedDataUseCase
import com.complexparking.domain.useCase.LoginUseCase
import com.complexparking.domain.useCase.SetPermissionsUseCase
import com.complexparking.domain.useCase.SetWizardUseCase
import com.complexparking.domain.useCase.SplashScreenGetWizardCompleteUseCase
import com.complexparking.domain.useCase.SplashScreenSetWizardCompleteUseCase
import com.complexparking.domain.useCase.UpdateGuestCarUseCase
import com.complexparking.domain.useCase.GetUserDataUseCase
import com.complexparking.domain.useCase.UpdateParkingConfigurationUseCase
import com.complexparking.domain.useCase.GetValidUnitsUseCase
import com.complexparking.domain.useCase.SetPrinterStatusUseCase
import com.complexparking.domain.useCase.ValidateWizardUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { CreateGuestUseCase(get(), get(), get()) }
    single { LoginUseCase(get(), get()) }
    single { GetValidUnitsUseCase(get()) }
    single { SplashScreenSetWizardCompleteUseCase(get()) }
    single { SplashScreenGetWizardCompleteUseCase(get()) }
    single { LoadComplexUnitDataUseCase(get(), get(), get(), get()) }
    single { CheckoutGuestCarUseCase(get(), get()) }
    single { UpdateGuestCarUseCase(get()) }
    single { GetSessionUseCase(get()) }
    single { SetWizardUseCase(get()) }
    single { GetUserDataUseCase(get()) }
    single { ValidateWizardUseCase(get()) }
    single { LoadSeedDataUseCase(get(), get(), get()) }
    single { GetPermissionsUseCase(get()) }
    single { SetPermissionsUseCase(get()) }
    single { CloseSessionUseCase(get()) }
    single { CreateUserUseCase(get()) }
    single { GetComplexConfigurationUseCase(get()) }
    single { UpdateParkingConfigurationUseCase(get(), get(), get()) }
    single { GetPrinterStatusUseCase(get()) }
    single { SetPrinterStatusUseCase(get()) }
    single { GetCashClosingDataUseCase(get(), get(), get()) }
}