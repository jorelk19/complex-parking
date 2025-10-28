package com.complexparking.ui.wizard

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.complexparking.R
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.Dimensions.spacingMedium
import com.complexparking.ui.base.EnumEditTextType
import com.complexparking.ui.base.ContainerWithScroll
import org.koin.androidx.compose.koinViewModel

@Composable
fun WizardComplexConfigurationScreen() {
    val wizardScreenViewModel: WizardScreenViewModel = koinViewModel()
    val model = wizardScreenViewModel.wizardModel.collectAsStateWithLifecycle()
    ContainerWithScroll(
        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.wizard_complex_configuration_title),
                modifier = Modifier.fillMaxSize()
            )
        },
        body = {
            WizardComplexConfigurationBody(
                model.value,
                onComplexNameChange = { wizardScreenViewModel.onComplexNameChange(it) },
                onUnitChange = { wizardScreenViewModel.onUnitChange(it) },
                onParkingChange = { wizardScreenViewModel.onParkingChange(it) },
                onAddressChange = { wizardScreenViewModel.onAddressChange(it) },
                onAdminChange = { wizardScreenViewModel.onAdminChange(it) }
            )
        }
    )
}

@Composable
private fun WizardComplexConfigurationBody(
    wizardScreenModel: WizardScreenModel,
    onComplexNameChange: (String) -> Unit,
    onUnitChange: (String) -> Unit,
    onParkingChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onAdminChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacingMedium)
    ) {
        CustomEditText(
            text = wizardScreenModel.complexName,
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
            text = wizardScreenModel.quantityUnit,
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
            text = wizardScreenModel.parkingQuantity,
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
            text = wizardScreenModel.complexAddress,
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
            text = wizardScreenModel.adminName,
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
}

@Composable
@Preview(showBackground = true)
fun WizardComplexConfigurationBodyPreview() {
    WizardComplexConfigurationBody(
        WizardScreenModel(),
        {},
        {},
        {},
        {},
        {}
    )
}
