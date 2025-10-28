package com.complexparking.utils.printerTools

import android.graphics.Bitmap

data class PrinterData(
    val plate: String,
    val complexName: String,
    val date: String,
    val qr: Bitmap?
)