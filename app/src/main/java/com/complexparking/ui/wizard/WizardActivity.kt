package com.complexparking.ui.wizard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.complexparking.ui.controls.SnackBarControl
import com.complexparking.ui.theme.ComplexParkingTheme
import com.complexparking.ui.utilities.LoadingManager

class WizardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            ComplexParkingTheme {
                Scaffold(
                    snackbarHost = {
                        SnackBarControl(snackbarHostState)
                    },
                    content = {
                        Column(modifier = Modifier.padding(it)) {
                            LoadingManager.showLoader()
                            WizardScreen(navHostController)
                        }
                    }
                )
            }
        }
    }
}