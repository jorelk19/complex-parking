package com.complexparking.di

import com.complexparking.ui.home.HomeScreenViewModel
import com.complexparking.ui.login.LoginScreenViewModel
import com.complexparking.ui.permissions.PermissionsViewModel
import com.complexparking.ui.printer.PrinterViewModel
import com.complexparking.ui.search.SearchScreenViewModel
import com.complexparking.ui.splash.SplashScreenViewModel
import com.complexparking.ui.wizard.WizardScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PrinterViewModel(get(), get()) }
    viewModel { LoginScreenViewModel(get()) }
    viewModel { HomeScreenViewModel(get(), get(), get()) }
    viewModel { WizardScreenViewModel(get(), get(), get()) }
    viewModel { SplashScreenViewModel(get(), get()) }
    viewModel { SearchScreenViewModel(get()) }
    viewModel { PermissionsViewModel(get(), get()) }
}