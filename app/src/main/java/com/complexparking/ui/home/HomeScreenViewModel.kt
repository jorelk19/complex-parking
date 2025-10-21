package com.complexparking.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.CreateGuestUseCase
import com.complexparking.domain.useCase.ValidateUnitUseCase
import com.complexparking.entities.CarGuest
import com.complexparking.ui.base.BaseViewModel
import com.complexparking.ui.utilities.ErrorType
import com.complexparking.utils.extensions.stringToFormat
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val validateUnitUseCase: ValidateUnitUseCase,
    private val createGuestUnitUseCase: CreateGuestUseCase,
) : BaseViewModel() {

    private val _homeScreenModel = mutableStateOf(HomeScreenModel())
    val homeScreenModel get() = _homeScreenModel

    private val plate = mutableStateOf("")
    private val unitToVisit = mutableStateOf("")

    private val _printFile = mutableStateOf(false)
    val printFile get() = _printFile

    init {
        loadHomeModel()
    }

    private fun loadHomeModel() {
        val currentDate = Date()
        plate.value = ""
        unitToVisit.value = ""
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

        val carGuest = CarGuest(
            plate = plate.value,
            hourStart = Calendar.getInstance().timeInMillis,
            date = Calendar.getInstance().time,
            isInComplex = true,
            house = unitToVisit.value?.toInt() ?: 0,
            hourEnd = null
        )
        viewModelScope.launch {
            try {
                createGuestUnitUseCase.execute(carGuest).collect { useCaseResult ->
                    validateUseCaseResult(
                        useCaseResult = useCaseResult,
                        resultAction = { result ->
                            if (result) {
                                loadHomeModel()
                                _printFile.value = true
                            } else {
                                /*Show error*/
                                false
                            }
                        }
                    )
                }


            } catch (e: Exception) {
                val msg = e.message
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
            validateUnit(unit)
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
                validateUseCaseResult(
                    useCaseResult = useCaseResult,
                    resultAction = { result ->
                        if (result) {
                            unitToVisit.value = unit
                            homeScreenModel.value = homeScreenModel.value.copy(
                                unitErrorType = ErrorType.NONE,
                                unitError = false,
                                unit = unit
                            )
                        } else {
                            homeScreenModel.value = homeScreenModel.value.copy(
                                unitErrorType = ErrorType.INVALID_UNIT,
                                unitError = true
                            )
                        }
                        validateButton()
                    }
                )
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
        } else {
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
        val enabled = if (homeScreenModel.value.unitError || homeScreenModel.value.plateError) {
            false
        } else {
            true
        }
        homeScreenModel.value = homeScreenModel.value.copy(
            isButtonRegisterEnabled = enabled
        )
    }
}