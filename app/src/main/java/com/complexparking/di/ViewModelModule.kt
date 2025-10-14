package com.complexparking.di

import com.complexparking.ui.home.HomeScreenViewModel
import com.complexparking.ui.login.LoginScreenViewModel
import com.complexparking.ui.splash.SplashScreenViewModel
import com.complexparking.ui.wizard.BaseWizardViewModel
import com.complexparking.ui.wizard.WizardScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginScreenViewModel(get()) }
    viewModel { HomeScreenViewModel(get(), get()) }
    viewModel { BaseWizardViewModel() }
    viewModel { WizardScreenViewModel(get(), get()) }
    viewModel { SplashScreenViewModel(get()) }
}