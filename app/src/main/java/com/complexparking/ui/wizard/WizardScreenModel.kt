package com.complexparking.ui.wizard

data class WizardScreenModel(
    val complexName: String = "",
    val quantityUnit: String = "",
    val complexAddress: String = "",
    val parkingQuantity: String = "",
    val onComplexNameChange: (String) -> Unit = {},
    val isButtonEnabled: Boolean = false,
    val buttonText: Int = 0,
    val onUnitChange: (String) -> Unit = {},
    val onAddressChange: (String) -> Unit = {},
    val onParkingChange: (String) -> Unit = {},
    val onClickNextStep: (EnumWizardStep) -> Unit = {},
    val onClickPreviousStep: (EnumWizardStep) -> Unit = {}
)
