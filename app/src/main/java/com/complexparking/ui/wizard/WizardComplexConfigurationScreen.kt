package com.complexparking.ui.wizard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithScroll
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.settings.menuScreens.ParkingSettingsState
import com.complexparking.ui.widgets.ParkingComplexConfigurationView
import org.koin.androidx.compose.koinViewModel

@Composable
fun WizardComplexConfigurationScreen() {
    val wizardScreenViewModel: WizardScreenViewModel = koinViewModel()
    val parkingSettingsState by wizardScreenViewModel.parkingSettingState.collectAsStateWithLifecycle()
    WizardComplexConfigurationBody(
        parkingSettingsState,
        onComplexNameChange = { wizardScreenViewModel.onComplexNameChange(it) },
        onUnitChange = { wizardScreenViewModel.onUnitChange(it) },
        onParkingChange = { wizardScreenViewModel.onParkingChange(it) },
        onAddressChange = { wizardScreenViewModel.onAddressChange(it) },
        onAdminChange = { wizardScreenViewModel.onAdminChange(it) },
        onParkingHourPriceChange = { wizardScreenViewModel.onParkingHourPriceChange(it) },
        onParkingMaxFreeHourChange = { wizardScreenViewModel.onParkingMaxFreeHourChange(it) }
    )
}

@Composable
private fun WizardComplexConfigurationBody(
    parkingSettingsState: ParkingSettingsState,
    onComplexNameChange: (String) -> Unit,
    onUnitChange: (String) -> Unit,
    onParkingChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onAdminChange: (String) -> Unit,
    onParkingMaxFreeHourChange: (String) -> Unit,
    onParkingHourPriceChange: (String) -> Unit,
) {
    ContainerWithScroll(
        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.wizard_complex_configuration_title),
                modifier = Modifier.fillMaxSize()
            )
        },
        body = {
            ParkingComplexConfigurationView(
                parkingSettingsState,
                onComplexNameChange,
                onUnitChange,
                onParkingChange,
                onAddressChange,
                onAdminChange,
                onParkingMaxFreeHourChange,
                onParkingHourPriceChange
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
fun WizardComplexConfigurationBodyPreview() {
    WizardComplexConfigurationBody(
        ParkingSettingsState(),
        {},
        {},
        {},
        {},
        {},
        {},
        {}
    )
}
