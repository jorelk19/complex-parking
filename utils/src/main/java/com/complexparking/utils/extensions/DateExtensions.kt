package com.complexparking.utils.extensions

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

const val DEFAULT_FORMAT = "dd/MM/yyyy"
const val DATE_HOUR_FORMAT = "dd/MM/yyyy HH:mm:ss"

const val HOUR_FORMAT = "hh:mm a"

private val hourFormat = SimpleDateFormat(HOUR_FORMAT, Locale.getDefault())
private val dateHourFormat = SimpleDateFormat(DATE_HOUR_FORMAT, Locale.getDefault())
private val formatter = SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault())

fun getCurrentTime(): String {
    val currentDate = Date()
    val formattedDate = hourFormat.format(currentDate)
    return formattedDate
}

fun getCurrentDate(): String {
    val currentDate = Date()
    val formattedDate = formatter.format(currentDate)
    return formattedDate
}

fun getDateFromString(stringDate: String): Date {
    return dateHourFormat.parse(stringDate) ?: Date()
}

fun getFormatStringDate(currentDate: String): String {
    return formatter.format(dateHourFormat.parse(currentDate))
}


fun Date.stringToFormat(format: String = DEFAULT_FORMAT): String {
    val dateFormat = SimpleDateFormat(format)
    return dateFormat.format(this)
}
