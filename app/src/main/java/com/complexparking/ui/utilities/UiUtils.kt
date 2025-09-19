package com.complexparking.ui.utilities

import android.util.Patterns

private fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun String.isValidEmail(onTrue: (input: String) -> Unit = {}, onFalse: () -> Unit = {}, onEmptyString: () -> Unit = {}) {
    this.let {
        if (it.isEmpty()) {
            onEmptyString.invoke()
        } else {
            if (isValidEmail(it.toString())) {
                onTrue.invoke(it.toString())
            } else {
                onFalse.invoke()
            }
        }
    }
}

private val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-!@#$%^&*()_+?.])[A-Za-z\\d-!@#$%^&*()_+?.]{8,}$".toRegex()

private fun isValidPassword(password: String): Boolean {
    return passwordRegex.containsMatchIn(password)
}

fun String.isValidPassword(onTrue: (input: String) -> Unit = {}, onFalse: () -> Unit = {}, onEmptyString: () -> Unit = {}) {
    this.let {
        if (it.isEmpty()) {
            onEmptyString.invoke()
        } else {
            if (isValidPassword(it.toString())) {
                onTrue.invoke(it.toString())
            } else {
                onFalse.invoke()
            }
        }
    }
}