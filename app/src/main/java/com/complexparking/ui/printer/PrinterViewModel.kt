package com.complexparking.ui.printer

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.GetPrinterStatusUseCase
import com.complexparking.domain.useCase.SetPrinterStatusUseCase
import com.complexparking.entities.PrinterStatus
import com.complexparking.ui.base.BaseViewModel
import com.complexparking.utils.printerTools.PrinterData
import com.complexparking.utils.printerTools.PrinterUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Data class to hold the UI state
data class PrinterUiState(
    val pairedDevices: List<BluetoothDevice> = emptyList(),
    val connectionStatus: String = "Disconnected",
    val isConnected: Boolean = false,
    val isConnecting: Boolean = false,
)

class PrinterViewModel(
    private val printerUtil: PrinterUtil,
    private val setPrinterStatusUseCase: SetPrinterStatusUseCase,
    private val getPrinterStatusUseCase: GetPrinterStatusUseCase,
) : BaseViewModel() {

    // UI state exposed to the Composable
    private val _uiState = MutableStateFlow(PrinterUiState())
    val uiState: StateFlow<PrinterUiState> = _uiState.asStateFlow()

    private val printerStatus = mutableStateOf(PrinterStatus())
    fun isBluetoothEnabled(): Boolean = printerUtil.isBluetoothEnabled()

    @SuppressLint("MissingPermission")
    fun getPairedDevices() {
        // Ensure permissions are granted before calling
        val devices = printerUtil.getPairedDevices()
        _uiState.update { it.copy(pairedDevices = devices?.toList() ?: emptyList()) }
    }

    fun connectToDevice(deviceAddress: String) {
        _uiState.update { it.copy(isConnecting = true, connectionStatus = "Connecting...") }
        printerUtil.connect(deviceAddress) { isSuccess, message ->
            if (isSuccess) {
                savePrinterStatus(deviceAddress)
            }
            _uiState.update {
                it.copy(
                    isConnected = isSuccess,
                    connectionStatus = message,
                    isConnecting = false
                )
            }
        }
    }

    private fun savePrinterStatus(deviceAddress: String) {
        viewModelScope.launch {
            setPrinterStatusUseCase.execute(
                PrinterStatus(
                    isPaired = true,
                    pairedDeviceAddress = deviceAddress
                )
            ).collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    if (result) {
                        //show message
                    }
                }
            }
        }
    }

    fun printMessage(printerData: PrinterData) {
        if (!_uiState.value.isConnected) {
            _uiState.update { it.copy(connectionStatus = "Error: Not connected") }
            return
        }

        printerUtil.print(printerData) { isSuccess, resultMessage ->
            // You can update the UI based on the printing result if needed
            // For example, show a Toast or update a status text
            println("Print result: $resultMessage")
        }
    }

    fun disconnect() {
        printerUtil.disconnect()
        _uiState.update {
            it.copy(
                isConnected = false,
                connectionStatus = "Disconnected"
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Ensure disconnection when the ViewModel is cleared
        disconnect()
    }

    fun existingConnection(printerData: PrinterData) {
        if (printerStatus.value.pairedDeviceAddress.isNotEmpty()) {
            connectToDevice(printerStatus.value.pairedDeviceAddress)
            printMessage(printerData)
        } else {
            disconnect()
        }
    }

    override fun onStartScreen() {
        viewModelScope.launch {
            getPrinterStatusUseCase.execute().collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    printerStatus.value = result
                    if(result.isPaired) {
                        connectToDevice(result.pairedDeviceAddress)
                    }
                }
            }
        }
    }
}
