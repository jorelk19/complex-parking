package com.complexparking.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.complexparking.R
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.CustomTextLageBold
import com.complexparking.ui.base.Dimensions.size20dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.Dimensions.spacingMedium
import com.complexparking.ui.base.EnumEditTextType
import com.complexparking.ui.settings.menuScreens.ParkingSettingsState


@Composable
fun ParkingComplexConfigurationView(
    parkingSettingsState: ParkingSettingsState,
    onComplexNameChange: (String) -> Unit,
    onUnitChange: (String) -> Unit,
    onParkingChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onAdminChange: (String) -> Unit,
    onParkingMaxFreeHourChange: (String) -> Unit,
    onParkingHourPriceChange: (String) -> Unit,
) {
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
                text = parkingSettingsState.complexName,
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
                text = parkingSettingsState.quantityUnit,
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
                text = parkingSettingsState.parkingQuantity,
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
                text = parkingSettingsState.complexAddress,
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
                text = parkingSettingsState.adminName,
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
                parkingHourPrice = parkingSettingsState.parkingHourPrice,
                parkingMaxFreeHour = parkingSettingsState.parkingMaxHourFree,
                onParkingMaxFreeHourChange = onParkingMaxFreeHourChange,
                onParkingHourPriceChange = onParkingHourPriceChange
            )
        }
    }
}

@Preview
@Composable
fun ParkingComplexConfigurationViewPreview() {
    ParkingComplexConfigurationView(
        parkingSettingsState = ParkingSettingsState(),
        onComplexNameChange = {},
        onUnitChange = {},
        onParkingChange = {},
        onAddressChange = {},
        onAdminChange = {},
        onParkingMaxFreeHourChange = {},
        onParkingHourPriceChange = {}
    )
}