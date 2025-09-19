package com.complexparking.di

import com.complexparking.ui.home.HomeScreenViewModel
import com.complexparking.ui.login.LoginScreenViewModel
import com.complexparking.ui.splash.SplashScreenViewModel
import com.complexparking.ui.wizard.WizardScreenViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { LoginScreenViewModel(get()) }
    single { HomeScreenViewModel(get(), get()) }
    single { WizardScreenViewModel(get()) }
    single { SplashScreenViewModel(get()) }
}