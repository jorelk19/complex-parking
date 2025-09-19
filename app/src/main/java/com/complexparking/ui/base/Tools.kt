package com.complexparking.ui.base

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Tools
 * MiPayway Android
 * Created by enieto on 2/18/2025
 * Copyright © 2025 Globant-CO. All rights reserved.
 */

@Composable
fun SystemBarColorManager(
    darkTheme: Boolean = isSystemInDarkTheme(),
    statusBarColorColor: Color? = null,
    navigationBarColor: Color? = null
) {
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window

        statusBarColorColor?.let { color ->
            window.statusBarColor = color.toArgb()
        }

        navigationBarColor?.let {
            window.navigationBarColor = it.toArgb()
        }

        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }
}