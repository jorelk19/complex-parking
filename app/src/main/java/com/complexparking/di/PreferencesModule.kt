package com.complexparking.di

import android.content.Context
import com.complexparking.utils.preferences.StorePreferenceUtils
import org.koin.dsl.module


fun preferenceModule(context: Context) = module {
    single { StorePreferenceUtils(context) }
}