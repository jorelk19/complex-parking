package com.complexparking.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.interfaces.ICreateGuestUnitUseCase
import com.complexparking.domain.interfaces.IValidateUnitUseCase
import com.complexparking.entities.Visitor
import com.complexparking.ui.utilities.ErrorType
import com.complexparking.utils.extensions.stringToFormat
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val validateUnitUseCase: IValidateUnitUseCase,
    private val createVisitorUnitUseCase: ICreateGuestUnitUseCase,
) : ViewModel() {

    private val _homeScreenModel = mutableStateOf(HomeScreenModel())
    val homeScreenModel get() = _homeScreenModel


    private val plate = MutableLiveData<String>()
    private val unitToVisit = MutableLiveData<String>()

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
        if (plate.value == null || unitToVisit.value == null) {
            return
        }

        val visitor = Visitor(
            plate = plate.value ?: "",
            hourStart = Calendar.getInstance().timeInMillis,
            date = Calendar.getInstance().time,
            isInComplex = true,
            house = unitToVisit.value?.toInt() ?: 0,
            hourEnd = null
        )
        viewModelScope.launch {
            runCatching {
                //TODO: Search plate to validate if exists in the unit owners
                createVisitorUnitUseCase.createVisitor(visitor)
            }.onSuccess { isSuccess ->
                if (isSuccess) {
                    clearForm()
                }
            }
        }
    }

    private fun clearForm() {
        homeScreenModel.value = homeScreenModel.value.copy(
            plateFocus = true
        )
        //loadHomeModel()
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
            runCatching {
                validateUnitUseCase.validateUnit(unit.toInt())
            }.onSuccess { isValidUnit ->
                if (isValidUnit) {
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
            }.onFailure {}
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