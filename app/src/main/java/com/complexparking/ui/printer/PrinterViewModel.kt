package com.complexparking.ui.printer

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.complexparking.utils.preferences.DEFAULT_PRINTER
import com.complexparking.utils.preferences.StorePreferenceUtils
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
    private val storePreferenceUtils: StorePreferenceUtils,
) : ViewModel() {

    // UI state exposed to the Composable
    private val _uiState = MutableStateFlow(PrinterUiState())
    val uiState: StateFlow<PrinterUiState> = _uiState.asStateFlow()

    fun isBluetoothEnabled(): Boolean = printerUtil.isBluetoothEnabled()

    // Permissions needed for Bluetooth operations
    /*    val requiredPermissions: Array<String>
            get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
            } else {
                arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION)
            }*/

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
                storePreferenceUtils.putString(DEFAULT_PRINTER, deviceAddress)
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

    fun existingConnection() {
        val device = storePreferenceUtils.getString(DEFAULT_PRINTER, "").toString()
        if (device.isNotEmpty()) {
            connectToDevice(device)
            //printMessage(printData)
        } else {
            disconnect()
        }
    }
}
