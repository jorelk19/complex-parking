package com.complexparking.ui.wizard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.Dimensions.spacingMedium
import com.complexparking.ui.base.EnumEditTextType
import com.complexparking.ui.base.MainContainer
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

@Composable
fun WizardComplexConfigurationScreen(navController: NavController) {
    val wizardScreenViewModel: WizardScreenViewModel by inject(WizardScreenViewModel::class.java)
    MainContainer(
        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.wizard_complex_configuration_title),
                modifier = Modifier.fillMaxSize()
            )
        },
        body = {
            WizardComplexConfigurationBody(
                navController,
                wizardScreenViewModel.wizardModel.value
            )
        }
    )
}

@Composable
private fun WizardComplexConfigurationBody(navController: NavController, wizardScreenModel: WizardScreenModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacingMedium)
    ) {
        CustomEditText(
            text = wizardScreenModel.complexName,
            titleText = stringResource(id = R.string.wizard_complex_configuration_name),
            onValueChange = { text ->
                wizardScreenModel.onComplexNameChange(text)
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
                wizardScreenModel.onUnitChange(text)
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
                wizardScreenModel.onParkingChange(text)
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
                wizardScreenModel.onAddressChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_address),
            hasFocus = false,
            bottomText = "",
            typeText = EnumEditTextType.DEFAULT
        )
    }
}
