package com.complexparking.utils.printerTools

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.OutputStream
import java.util.*

class PrinterUtil(private val context: Context) {

    private val bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    private var socket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    // Standard UUID for Serial Port Profile (SPP)
    private val sppUuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    /**
     * Checks if the device supports Bluetooth.
     */
    fun isBluetoothSupported(): Boolean = bluetoothAdapter != null

    /**
     * Checks if Bluetooth is enabled.
     */
    fun isBluetoothEnabled(): Boolean = bluetoothAdapter?.isEnabled == true

    /**
     * Returns a set of paired Bluetooth devices.
     * Requires BLUETOOTH_CONNECT permission on API 31+.
     */
    @SuppressLint("MissingPermission")
    fun getPairedDevices(): Set<BluetoothDevice>? {
        // BLUETOOTH_CONNECT permission is required.
        // Handle permissions in your Activity/Fragment.
        return bluetoothAdapter?.bondedDevices
    }

    /**
     * Connects to a specific Bluetooth printer by its MAC address.
     * This should be run on a background thread.
     */
    @SuppressLint("MissingPermission")
    fun connect(deviceAddress: String, onResult: (Boolean, String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            if (bluetoothAdapter == null) {
                onResult(false, "Bluetooth is not supported on this device.")
                return@launch
            }
            if (!bluetoothAdapter.isEnabled) {
                onResult(false, "Bluetooth is not enabled.")
                return@launch
            }

            // Cancel discovery because it otherwise slows down the connection.
            bluetoothAdapter.cancelDiscovery()

            val device: BluetoothDevice? = bluetoothAdapter.getRemoteDevice(deviceAddress)
            if (device == null) {
                onResult(false, "Device not found.")
                return@launch
            }

            try {
                // Create a BluetoothSocket for the connection
                socket = device.createRfcommSocketToServiceRecord(sppUuid)
                socket?.connect()
                outputStream = socket?.outputStream
                onResult(true, "Connected successfully.")
            } catch (e: IOException) {
                e.printStackTrace()
                onResult(false, "Connection failed: ${e.message}")
                disconnect() // Ensure resources are released on failure
            }
        }
    }

    /**
     * Sends data (as a string) to the connected printer.
     * This must be called after a successful connection.
     */
    fun print(data: String, onResult: (Boolean, String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            if (outputStream == null) {
                onResult(false, "Printer is not connected.")
                return@launch
            }

            try {
                // Most thermal printers expect specific command sets (like ESC/POS).
                // Here we send the raw string bytes, but for formatted text,
                // you would construct proper command sequences.
                // Appending a newline is common to ensure the printer prints the line.
                outputStream?.write((data + "\n").toByteArray())
                outputStream?.flush()
                onResult(true, "Data sent to printer.")
            } catch (e: IOException) {
                e.printStackTrace()
                onResult(false, "Failed to send data: ${e.message}")
            }
        }
    }

    /**
     * Disconnects from the printer and releases resources.
     */
    fun disconnect() {
        try {
            outputStream?.close()
            socket?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            outputStream = null
            socket = null
        }
    }
}
