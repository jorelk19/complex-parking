package com.complexparking.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date

const val DEFAULT_FORMAT = "dd/MM/yyyy"

fun Date.stringToFormat(format: String = DEFAULT_FORMAT): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(this)
}
