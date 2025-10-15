package com.complexparking.ui.utilities

import android.util.Patterns
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner

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

fun String.formatPlate(): String {
    return if (this.length > 3) {
        val sb = StringBuilder(this)
        sb.insert(3, "-")
        sb.toString().uppercase()
    } else {
        this.uppercase()
    }
}

@Composable
fun ObservableScreen(
    onDestroyed: () -> Unit = {},
    onInitialized: () -> Unit = {},
    onCreated: () -> Unit = {},
    onStarted: () -> Unit = {},
    onResume: () -> Unit = {},
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        // Do something with your state
        // You may want to use DisposableEffect or other alternatives
        // instead of LaunchedEffect
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> { onDestroyed() }
            Lifecycle.State.INITIALIZED -> { onInitialized() }
            Lifecycle.State.CREATED -> { onCreated() }
            Lifecycle.State.STARTED -> { onStarted() }
            Lifecycle.State.RESUMED -> { onResume() }
        }
    }
}