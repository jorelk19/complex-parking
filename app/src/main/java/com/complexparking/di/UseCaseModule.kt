package com.complexparking.di

import com.complexparking.domain.interfaces.ICreateGuestUnitUseCase
import com.complexparking.domain.interfaces.ILoadComplexUnitDataUseCase
import com.complexparking.domain.interfaces.ILoginUseCase
import com.complexparking.domain.interfaces.ISplashScreenUseCase
import com.complexparking.domain.interfaces.IValidateUnitUseCase
import com.complexparking.domain.useCase.CreateGuestUseCase
import com.complexparking.domain.useCase.LoadComplexUnitDataUseCase
import com.complexparking.domain.useCase.LoginUseCase
import com.complexparking.domain.useCase.SplashScreenUseCase
import com.complexparking.domain.useCase.ValidateUnitUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<ICreateGuestUnitUseCase> { CreateGuestUseCase(get()) }
    single<ILoginUseCase> { LoginUseCase() }
    single<IValidateUnitUseCase> { ValidateUnitUseCase() }
    single<ISplashScreenUseCase> { SplashScreenUseCase(get()) }
    single<ILoadComplexUnitDataUseCase> { LoadComplexUnitDataUseCase(get(), get()) }
}