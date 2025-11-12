package com.complexparking.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.CreateGuestUseCase
import com.complexparking.domain.useCase.GetValidUnitsUseCase
import com.complexparking.entities.CarGuest
import com.complexparking.ui.base.BaseViewModel
import com.complexparking.ui.printer.PrinterViewModel
import com.complexparking.ui.utilities.ErrorType
import com.complexparking.ui.utilities.formatPlate
import com.complexparking.utils.extensions.getCurrentDate
import com.complexparking.utils.extensions.getCurrentStringDate
import com.complexparking.utils.extensions.getCurrentTime
import com.complexparking.utils.extensions.getCurrentTime12H
import com.complexparking.utils.extensions.stringToFormat
import com.complexparking.utils.printerTools.PrinterData
import com.complexparking.utils.qrTools.QrUtils
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getValidUnitsUseCase: GetValidUnitsUseCase,
    private val createGuestUnitUseCase: CreateGuestUseCase,
    private val printerViewModel: PrinterViewModel,
) : BaseViewModel() {
    private val _homeScreenModel = mutableStateOf(HomeScreenModel())
    val homeScreenModel get() = _homeScreenModel

    private val plate = mutableStateOf("")
    private val unitToVisit = mutableStateOf("")
    private val carGuest = mutableStateOf(CarGuest())
    private val _printFile = mutableStateOf(false)
    private val _validUnits = mutableStateOf(0)
    val printFile get() = _printFile

    private fun loadHomeModel() {
        plate.value = ""
        unitToVisit.value = ""
        carGuest.value = CarGuest()
        homeScreenModel.value = HomeScreenModel(
            onTextPlateChange = { onPlateChange(it) },
            onTextUnitChange = { onUnitToVisitChange(it) },
            onRegisterButtonClick = { onRegisterClick() },
            hourArrive = getCurrentTime12H(),
            date = getCurrentStringDate(),
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
        registerCarGuest()
    }

    private fun registerCarGuest() {
        carGuest.value = CarGuest(
            plate = plate.value,
            isInComplex = true,
            complexUnit = unitToVisit.value.toInt(),
            hourEnd = null
        )
        viewModelScope.launch {
            createGuestUnitUseCase.execute(carGuest.value).collect { useCaseResult ->
                validateUseCaseResult(useCaseResult) { result ->
                    if (result) {
                        printProcess()
                        clearForm()
                        //_printFile.value = true
                    } else {
                        /*Existing car*/
                        false
                    }
                }
            }
        }
    }

    private fun clearForm() {
        /*homeScreenModel.value = homeScreenModel.value.copy(
            plateFocus = true
        )*/
        loadHomeModel()
    }

    private fun onUnitToVisitChange(unit: String) {
        if (unit.isNotEmpty()) {
            //unitToVisit.value = unit
            if (unit.toInt() in 1.._validUnits.value) {
                _homeScreenModel.value = _homeScreenModel.value.copy(
                    unitErrorType = ErrorType.NONE,
                    unitError = false,
                    unit = unit
                )
                unitToVisit.value = unit
            } else {
                _homeScreenModel.value = _homeScreenModel.value.copy(
                    unitErrorType = ErrorType.INVALID_UNIT,
                    unitError = true,
                    unit = unit
                )
            }
            /*getValidUnits(unit)*/
        } else {
            _homeScreenModel.value = _homeScreenModel.value.copy(
                unitErrorType = ErrorType.EMPTY_UNIT,
                unitError = true,
                unit = ""
            )
        }
        validateButton()
    }

    private fun getValidUnits() {
        viewModelScope.launch {
            getValidUnitsUseCase.execute().collect { useCaseResult ->
                validateUseCaseResult(useCaseResult) { result ->
                    _validUnits.value = result
                    /* if (result) {
                         unitToVisit.value = unit
                         //registerCarGuest()
                         *//* homeScreenModel.value = homeScreenModel.value.copy(
                             unitErrorType = ErrorType.NONE,
                             unitError = false,
                             unit = unit
                         )*//*
                    } else {
                        _homeScreenModel.value = _homeScreenModel.value.copy(
                            unitErrorType = ErrorType.INVALID_UNIT,
                            unitError = true
                        )
                    }
                    validateButton()*/
                }
            }
        }
    }

    private fun onPlateChange(plateText: String) {
        if (plateText.isEmpty()) {
            _homeScreenModel.value = _homeScreenModel.value.copy(
                plateText = plateText,
                plateErrorType = ErrorType.EMPTY_PLATE,
                plateError = true,
                isButtonRegisterEnabled = false
            )
        } else if (plateText.length < 6) {
            _homeScreenModel.value = _homeScreenModel.value.copy(
                plateErrorType = ErrorType.INVALID_PLATE,
                plateError = true,
                plateText = plateText,
                isButtonRegisterEnabled = false
            )
        } else if (plateText.length == 6) {
            _homeScreenModel.value = _homeScreenModel.value.copy(
                plateErrorType = ErrorType.NONE,
                plateError = false,
                plateText = plateText,
            )
            plate.value = plateText
        }
        validateButton()
    }

    private fun validateButton() {
        val enabled = if (_homeScreenModel.value.plateError ||
            _homeScreenModel.value.unitError ||
            plate.value.isEmpty() ||
            unitToVisit.value.isEmpty()
        ) {
            false
        } else {
            true
        }
        _homeScreenModel.value = _homeScreenModel.value.copy(
            isButtonRegisterEnabled = enabled
        )
    }

    override fun onStartScreen() {
        loadHomeModel()
        getValidUnits()
    }

    private fun printProcess() {
        val printerData = PrinterData(
            plate = carGuest.value.plate.formatPlate(),
            complexName = "Manzana 72",
            date = carGuest.value.date.toString(),
            qr = QrUtils.generateQRCode(carGuest.value.plate + "/" + carGuest.value.complexUnit),
            hour = carGuest.value.hourStart.toString()
        )
        printerViewModel.existingConnection(printerData)
    }

    fun observePrintState() {
        viewModelScope.launch {
            printerViewModel.uiState.collect { result ->
                if (result.isConnected) {
                    val printerData = PrinterData(
                        plate = carGuest.value.plate.formatPlate(),
                        complexName = "Manzana 72",
                        date = carGuest.value.date.stringToFormat(),
                        hour = carGuest.value.hourStart.stringToFormat(),
                        qr = QrUtils.generateQRCode(carGuest.value.plate)
                    )
                    printerViewModel.printMessage(printerData)
                    loadHomeModel()
                }
            }
        }
    }
}