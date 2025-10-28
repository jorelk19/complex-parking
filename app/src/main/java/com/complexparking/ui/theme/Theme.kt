package com.complexparking.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

val LocalCustomColors = staticCompositionLocalOf { lightColors }

@Composable
fun ComplexParkingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colorScheme = if (darkTheme) darkColors else lightColors

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            /*val controller = WindowInsetsControllerCompat(window, window.decorView)
            controller.isAppearanceLightStatusBars = false
            controller.isAppearanceLightNavigationBars = false*/
            window.statusBarColor = colorScheme.colorPrimaryBg.toArgb()
            window.navigationBarColor = colorScheme.colorPrimaryBg.toArgb()


           /* window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()*/

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = darkTheme
            //window.insetsController?.systemBarsBehavior = Color.Transparent.toArgb()
            //window.statusBarColor = colorScheme.colorPrimaryBg.toArgb()
        }
    }

    MaterialTheme {
        CompositionLocalProvider(
            LocalCustomColors provides colorScheme
        ) {
            content()
        }
    }
}