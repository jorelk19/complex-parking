package com.complexparking.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.CreateGuestUseCase
import com.complexparking.domain.useCase.ValidateUnitUseCase
import com.complexparking.entities.CarGuest
import com.complexparking.ui.base.BaseViewModel
import com.complexparking.ui.printer.PrinterViewModel
import com.complexparking.ui.utilities.ErrorType
import com.complexparking.ui.utilities.formatPlate
import com.complexparking.utils.extensions.stringToFormat
import com.complexparking.utils.printerTools.PrinterData
import com.complexparking.utils.qrTools.QrUtils
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val validateUnitUseCase: ValidateUnitUseCase,
    private val createGuestUnitUseCase: CreateGuestUseCase,
    private val printerViewModel: PrinterViewModel,
) : BaseViewModel() {
    private val _homeScreenModel = mutableStateOf(HomeScreenModel())
    val homeScreenModel get() = _homeScreenModel

    private val plate = mutableStateOf("")
    private val unitToVisit = mutableStateOf("")
    private val carGuest = mutableStateOf(CarGuest())
    private val _printFile = mutableStateOf(false)
    val printFile get() = _printFile

    /*   init {

       }*/
    private fun loadHomeModel() {
        val currentDate = Date()
        plate.value = ""
        unitToVisit.value = ""
        carGuest.value = CarGuest()
        homeScreenModel.value = HomeScreenModel(
            onClickHeaderBack = { onClickBack() },
            onTextPlateChange = { onPlateChange(it) },
            onTextUnitChange = { onUnitToVisitChange(it) },
            onRegisterButtonClick = { onRegisterClick() },
            hourArrive = Calendar.getInstance().time.toString(),
            date = currentDate.stringToFormat(),
            plateText = "",
            unit = "",
            unitError = false,
            plateError = false,
            plateFocus = true,
            plateErrorType = ErrorType.NONE,
            unitErrorType = ErrorType.NONE
        )
    }

    private fun onRegisterClick() {
        if (plate.value.isEmpty() || unitToVisit.value.isEmpty()) {
            return
        }
        validateUnit(unitToVisit.value)
    }

    private fun registerCarGuest() {
        carGuest.value = CarGuest(
            plate = plate.value,
            hourStart = Calendar.getInstance().timeInMillis,
            date = Calendar.getInstance().time,
            isInComplex = true,
            house = unitToVisit.value?.toInt() ?: 0,
            hourEnd = null
        )
        viewModelScope.launch {
            createGuestUnitUseCase.execute(carGuest.value).collect { useCaseResult ->
                validateUseCaseResult(useCaseResult) { result ->
                    if (result) {
                        printProcess()
                        //_printFile.value = true

                    } else {
                        /*Show error*/
                        false
                    }
                }
            }
        }
    }

    private fun clearForm() {
        homeScreenModel.value = homeScreenModel.value.copy(
            plateFocus = true
        )
        loadHomeModel()
    }

    private fun onUnitToVisitChange(unit: String) {
        if (unit.isNotEmpty()) {
            unitToVisit.value = unit
            /*validateUnit(unit)*/
        } else {
            homeScreenModel.value = homeScreenModel.value.copy(
                unitErrorType = ErrorType.EMPTY_UNIT,
                unitError = true
            )
            validateButton()
        }
    }

    private fun validateUnit(unit: String) {
        viewModelScope.launch {
            validateUnitUseCase.execute(unit.toInt()).collect { useCaseResult ->
                validateUseCaseResult(useCaseResult) { result ->
                    if (result) {
                        /*unitToVisit.value = unit*/
                        registerCarGuest()
                        /* homeScreenModel.value = homeScreenModel.value.copy(
                             unitErrorType = ErrorType.NONE,
                             unitError = false,
                             unit = unit
                         )*/
                    } else {
                        homeScreenModel.value = homeScreenModel.value.copy(
                            unitErrorType = ErrorType.INVALID_UNIT,
                            unitError = true
                        )
                    }
                    validateButton()
                }
            }
        }
    }

    private fun onClickBack() {
    }

    private fun onPlateChange(plateText: String) {
        if (plateText.isEmpty()) {
            homeScreenModel.value = homeScreenModel.value.copy(
                plateText = plateText,
                plateErrorType = ErrorType.EMPTY_PLATE,
                plateError = true,
                isButtonRegisterEnabled = false
            )
        } else if (plateText.length < 6) {
            homeScreenModel.value = homeScreenModel.value.copy(
                plateErrorType = ErrorType.INVALID_PLATE,
                plateError = true,
                plateText = plateText,
                isButtonRegisterEnabled = false
            )
        } else if (plateText.length == 6) {
            homeScreenModel.value = homeScreenModel.value.copy(
                plateErrorType = ErrorType.NONE,
                plateError = false,
                plateText = plateText,
            )
            plate.value = plateText
        }
        validateButton()
    }

    private fun validateButton() {
        val enabled = if (homeScreenModel.value.plateError) {
            false
        } else {
            true
        }
        homeScreenModel.value = homeScreenModel.value.copy(
            isButtonRegisterEnabled = enabled
        )
    }

    override fun onStartScreen() {
        loadHomeModel()
        viewModelScope.launch {
            printerViewModel.uiState.collect { result ->
                if (result.isConnected) {
                    val printerData = PrinterData(
                        plate = carGuest.value.plate.formatPlate(),
                        complexName = "Manzana 72",
                        date = carGuest.value.date.toString(),
                        qr = QrUtils.generateQRCode(carGuest.value.plate)
                    )
                    printerViewModel.printMessage(printerData)
                    loadHomeModel()
                }
            }
        }
    }

    private fun printProcess() {
        printerViewModel.existingConnection()
    }
}