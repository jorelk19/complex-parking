package com.complexparking.domain.useCase

import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.PrinterStatus
import com.complexparking.ui.utilities.fromJson
import com.complexparking.utils.preferences.PRINTER_STATUS_INFO
import com.complexparking.utils.preferences.StorePreferenceUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPrinterStatusUseCase(
    private val storePreferenceUtils: StorePreferenceUtils,
) : BaseUseCase<Any, PrinterStatus> {
    override suspend fun execute(params: Any?): Flow<ResultUseCaseState<PrinterStatus>> = flow {
        try {
            val data = storePreferenceUtils.getString(PRINTER_STATUS_INFO, "")
            data?.let {
                if (data.isNotEmpty()) {
                    val printerStatus = it.fromJson<PrinterStatus>()
                    emit(ResultUseCaseState.Success(printerStatus))
                } else {
                    emit(ResultUseCaseState.Success(PrinterStatus(isPaired = false, pairedDeviceAddress = "")))
                }
            }
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}