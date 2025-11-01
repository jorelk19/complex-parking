package com.complexparking.ui.wizard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithScroll
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.CustomTextLageBold
import com.complexparking.ui.base.Dimensions.size20dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.Dimensions.spacingMedium
import com.complexparking.ui.base.EnumEditTextType
import com.complexparking.ui.widgets.CustomSurface
import com.complexparking.ui.widgets.ParkingParameterSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun WizardComplexConfigurationScreen() {
    val wizardScreenViewModel: WizardScreenViewModel = koinViewModel()
    val uiState by wizardScreenViewModel.wizardModel.collectAsStateWithLifecycle()
    WizardComplexConfigurationBody(
        uiState,
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
    wizardScreenState: WizardScreenState,
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacingMedium)
            ) {
                CustomTextLageBold(
                    text = stringResource(R.string.wizard_complex_configuration_section_complex_data_title)
                )
                CustomSurface(
                    modifier = Modifier.padding(bottom = size20dp)
                ) {
                    CustomEditText(
                        text = wizardScreenState.complexName,
                        titleText = stringResource(id = R.string.wizard_complex_configuration_name),
                        onValueChange = { text ->
                            onComplexNameChange(text)
                        },
                        imageStart = ImageVector.vectorResource(R.drawable.ic_house),
                        hasFocus = true,
                        bottomText = "",
                        typeText = EnumEditTextType.DEFAULT
                    )
                    Spacer(
                        modifier = Modifier.height(size5dp)
                    )
                    CustomEditText(
                        text = wizardScreenState.quantityUnit,
                        titleText = stringResource(id = R.string.wizard_complex_configuration_quantity_units),
                        onValueChange = { text ->
                            onUnitChange(text)
                        },
                        imageStart = ImageVector.vectorResource(R.drawable.ic_house_quantities),
                        hasFocus = false,
                        bottomText = "",
                        typeText = EnumEditTextType.NUMBER,
                        maxLength = 4
                    )
                    Spacer(
                        modifier = Modifier.height(size5dp)
                    )
                    CustomEditText(
                        text = wizardScreenState.parkingQuantity,
                        titleText = stringResource(id = R.string.wizard_complex_configuration_quantity_parking),
                        onValueChange = { text ->
                            onParkingChange(text)
                        },
                        imageStart = ImageVector.vectorResource(R.drawable.ic_parking_quantities),
                        hasFocus = false,
                        bottomText = "",
                        typeText = EnumEditTextType.NUMBER,
                        maxLength = 4
                    )
                    Spacer(
                        modifier = Modifier.height(size5dp)
                    )
                    CustomEditText(
                        text = wizardScreenState.complexAddress,
                        titleText = stringResource(id = R.string.wizard_complex_configuration_address),
                        onValueChange = { text ->
                            onAddressChange(text)
                        },
                        imageStart = ImageVector.vectorResource(R.drawable.ic_address),
                        hasFocus = false,
                        bottomText = "",
                        typeText = EnumEditTextType.DEFAULT
                    )
                    Spacer(
                        modifier = Modifier.height(size5dp)
                    )
                    CustomEditText(
                        text = wizardScreenState.adminName,
                        titleText = stringResource(id = R.string.wizard_complex_configuration_admin_name),
                        onValueChange = { text ->
                            onAdminChange(text)
                        },
                        imageStart = ImageVector.vectorResource(R.drawable.ic_user_admin),
                        hasFocus = false,
                        bottomText = "",
                        typeText = EnumEditTextType.DEFAULT
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(size5dp)
                        .padding(top = size5dp)
                )
                CustomTextLageBold(
                    text = stringResource(R.string.wizard_complex_configuration_section_parking_data_title)
                )
                CustomSurface(
                    modifier = Modifier.padding(bottom = size20dp)
                ) {
                    ParkingParameterSection(
                        parkingHourPrice = "",
                        parkingMaxFreeHour = "",
                        onParkingMaxFreeHourChange = onParkingMaxFreeHourChange,
                        onParkingHourPriceChange = onParkingHourPriceChange
                    )
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun WizardComplexConfigurationBodyPreview() {
    WizardComplexConfigurationBody(
        WizardScreenState(),
        {},
        {},
        {},
        {},
        {},
        {},
        {}
    )
}
