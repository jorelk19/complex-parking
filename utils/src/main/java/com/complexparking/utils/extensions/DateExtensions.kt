package com.complexparking.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DEFAULT_FORMAT = "dd/MM/yyyy"
const val DATE_HOUR_FORMAT = "dd/MM/yyyy HH:mm:ss"

const val HOUR_FORMAT_12H = "hh:mm a"
const val HOUR_FORMAT_24H = "hh:mm"

private val hourFormat12h = SimpleDateFormat(HOUR_FORMAT_12H, Locale.getDefault())
private val hourFormat24h = SimpleDateFormat(HOUR_FORMAT_24H, Locale.getDefault())

private val dateHourFormat = SimpleDateFormat(DATE_HOUR_FORMAT, Locale.getDefault())
private val formatter = SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault())

fun getCurrentTime12H(): String {
    val currentDate = Date()
    val formattedDate = hourFormat12h.format(currentDate)
    return formattedDate
}

fun getCurrentTime(): Long {
    val currentDate = Date()
    return currentDate.time
}

fun getCurrentDate(): Date {
    val currentDate = Date()
    return currentDate
}

fun getCurrentStringDate(): String {
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

fun Long.stringToFormat(format: String = HOUR_FORMAT_12H): String {
    val dateFormat = SimpleDateFormat(format)
    return dateFormat.format(this)
}
