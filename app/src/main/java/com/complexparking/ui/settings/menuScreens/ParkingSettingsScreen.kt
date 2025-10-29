package com.complexparking.ui.settings.menuScreens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithScroll
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.EnumEditTextType
import org.koin.androidx.compose.koinViewModel


@Composable
fun ParkingSettingsScreen() {
    val parkingSettingsScreenViewModel: ParkingSettingsScreenViewModel = koinViewModel()
    parkingSettingsScreenViewModel.isCompletedLoadingData.collectAsState()
    ContainerWithScroll(
        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.settings_screen_title),
                modifier = Modifier.fillMaxSize()
            )
        },
        body = {
            ParkingSettingsBody()
        }
    )
}

@Composable
fun ParkingSettingsBody() {
    /*CustomEditText(
        text = wizardScreenModel.complexName,
        titleText = stringResource(id = R.string.wizard_complex_configuration_name),
        onValueChange = { text ->
            onComplexNameChange(text)
        },
        imageStart = ImageVector.vectorResource(R.drawable.ic_house),
        hasFocus = true,
        bottomText = "",
        typeText = EnumEditTextType.DEFAULT
    )*/
}

@Composable
fun ParkingSettingsScreenPreview() {

}

