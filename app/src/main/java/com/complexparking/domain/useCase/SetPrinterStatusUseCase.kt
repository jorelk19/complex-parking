package com.complexparking.domain.useCase

import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.PrinterStatus
import com.complexparking.ui.utilities.json
import com.complexparking.utils.preferences.PRINTER_STATUS_INFO
import com.complexparking.utils.preferences.StorePreferenceUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SetPrinterStatusUseCase(
    private val storePreferenceUtils: StorePreferenceUtils,
) : BaseUseCase<PrinterStatus, Boolean> {
    override suspend fun execute(params: PrinterStatus?): Flow<ResultUseCaseState<Boolean>> = flow {
        try {
            params?.let {
                storePreferenceUtils.putString(PRINTER_STATUS_INFO, it.json())
                emit(ResultUseCaseState.Success(true))
            } ?: run {
                emit(ResultUseCaseState.Success(false))
            }
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}