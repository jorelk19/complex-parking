package com.complexparking.di

import android.content.Context
import com.complexparking.utils.printerTools.PrinterUtil
import org.koin.dsl.module

fun deviceModule(context: Context) = module {
    single { PrinterUtil(context) }
}