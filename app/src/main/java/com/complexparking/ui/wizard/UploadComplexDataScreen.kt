package com.complexparking.ui.wizard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.MainContainer
import com.complexparking.ui.theme.LocalCustomColors

@Composable
fun UploadComplexDataScreen(navController: NavController) {
    val colors = LocalCustomColors.current
    MainContainer(
        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.wizard_upload_complex_data_title),
                modifier = Modifier.fillMaxSize()
            )
        },
        body = {
            UploadComplexDataBody(
                navController
            )
        }
    )
}

@Composable
private fun UploadComplexDataBody(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {

    }
}
