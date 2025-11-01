package com.complexparking.ui.settings.menuScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithScroll
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size50dp
import com.complexparking.ui.navigation.AppScreens
import com.complexparking.ui.widgets.ParkingParameterSection
import org.koin.androidx.compose.koinViewModel


@Composable
fun ParkingSettingsScreen(navController: NavController) {
    val parkingSettingsScreenViewModel: ParkingSettingsScreenViewModel = koinViewModel()
    parkingSettingsScreenViewModel.isCompletedLoadingData.collectAsStateWithLifecycle()
    val uiState by parkingSettingsScreenViewModel.parkingSettingsModel.collectAsState()
    ParkingSettingsBody(
        uiState = uiState,
        onParkingHourPriceChange = { parkingSettingsScreenViewModel.onParkingHourPriceChange(it) },
        onParkingMaxFreeHourChange = { parkingSettingsScreenViewModel.onParkingMaxFreeHourChange(it) },
        onSaveParkingSettingsClick = { parkingSettingsScreenViewModel.onSaveSettings() },
        onClickBack = { navController.navigate(AppScreens.SETTINGSCREEN.route)}
    )
}

@Composable
fun ParkingSettingsBody(
    uiState: ParkingSettingsState,
    onParkingHourPriceChange: (String) -> Unit,
    onParkingMaxFreeHourChange: (String) -> Unit,
    onSaveParkingSettingsClick: () -> Unit,
    onClickBack: () -> Unit
) {
    ContainerWithScroll(
        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.parking_setting_screen_title),
                modifier = Modifier.fillMaxSize(),
                imageStart = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                onClickStart = { onClickBack() }
            )
        },
        body = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = size50dp, end = size50dp)
            ) {
                Spacer(
                    modifier = Modifier.height(size30dp)
                )
                ParkingParameterSection(
                    parkingHourPrice = uiState.parkingHourPrice,
                    parkingMaxFreeHour = uiState.parkingMaxFreeHour,
                    onParkingHourPriceChange = onParkingHourPriceChange,
                    onParkingMaxFreeHourChange = onParkingMaxFreeHourChange,
                    parkingHourHasFocus = true
                )
                Spacer(
                    modifier = Modifier.height(size30dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onSaveParkingSettingsClick()
                        },
                        buttonText = stringResource(id = R.string.parking_setting_screen_save_button),
                        isEnabled = uiState.isButtonEnabled
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ParkingSettingsScreenPreview() {
    ParkingSettingsBody(
        ParkingSettingsState(),
        {},
        {},
        {},
        {}
    )
}

