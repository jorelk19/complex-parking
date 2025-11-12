package com.complexparking.utils.printerTools

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    fun print(printerData: PrinterData, onResult: (Boolean, String) -> Unit) {
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
                val ESC_ALIGN_CENTER = byteArrayOf(0x1b, 0x61, 0x01) // Center alignment
                val ESC_ALIGN_RIGHT = byteArrayOf(0x1b, 0x61, 0x02)  // Right alignment
                val ESC_ALIGN_LEFT = byteArrayOf(0x1b, 0x61, 0x00)   // Left alignment (default)
                val ESC_BOLD_ON = byteArrayOf(0x1b, 0x45, 0x01) // Turn bold on
                val ESC_BOLD_OFF = byteArrayOf(0x1b, 0x45, 0x00) // Turn bold off
                val ESC_UNDERLINE_ON = byteArrayOf(0x1b, 0x2d, 0x01) // Turn underline on
                val ESC_UNDERLINE_OFF = byteArrayOf(0x1b, 0x2d, 0x00) // Turn underline off
                val ESC_DOUBLE_HEIGHT_WIDTH_ON = byteArrayOf(0x1b, 0x21, 0x30) // Double height and width
                val ESC_NORMAL_FONT = byteArrayOf(0x1b, 0x21, 0x00) // Normal font
                val SELECT_BIT_IMAGE_MODE = byteArrayOf(0x1B, 0x2A, 33)
                val LF = byteArrayOf(0x0A)

                outputStream?.write(ESC_ALIGN_CENTER)
                outputStream?.write("Parqueadero Conjunto \n ${printerData.complexName}\n".toByteArray())
                outputStream?.write(ESC_ALIGN_CENTER)
                outputStream?.write(ESC_BOLD_ON)
                outputStream?.write(ESC_DOUBLE_HEIGHT_WIDTH_ON)
                outputStream?.write(("Placa: ${printerData.plate }\n").toByteArray())
                outputStream?.write(ESC_NORMAL_FONT)
                outputStream?.write(LF)
                printerData.qr?.let {
                    outputStream?.write(SELECT_BIT_IMAGE_MODE)
                    val qr = BitmapHelper.decodeBitmap(it)
                    qr?.let {
                        outputStream?.write(qr)
                    }
                }
                outputStream?.write(LF)
                outputStream?.write(ESC_UNDERLINE_ON)
                outputStream?.write("Fecha de ingreso: ${printerData.date}".toByteArray())
                outputStream?.write(LF)
                outputStream?.write(LF)
                outputStream?.write("Hora de ingreso: ${printerData.hour}".toByteArray())
                outputStream?.write(ESC_UNDERLINE_OFF)
                outputStream?.write(ESC_BOLD_OFF)

                outputStream?.write(LF)
                outputStream?.write(LF)
                outputStream?.flush()
                onResult(true, "Data sent to printer.")
            } catch (e: IOException) {
                e.printStackTrace()
                onResult(false, "Failed to send data: ${e.message}")
            }
        }
    }

    fun getQrImage(text: String): Bitmap? {
        try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(
                text,
                BarcodeFormat.QR_CODE,
                300,
                300
            )

            // Option 1: Convert BitMatrix to Bitmap manually
            val bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
            for (x in 0 until 300) {
                for (y in 0 until 300) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
            return bitmap

            // Option 2: (Recommended for Android) Use BarcodeEncoder from zxing-android-embedded
            // You would need to add 'implementation("com.journeyapps:zxing-android-embedded:4.3.0")'
            // val barcodeEncoder = BarcodeEncoder()
            // return barcodeEncoder.createBitmap(bitMatrix)

        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }
    }

    fun convertBitmapToPrinterBytes(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        bitmap.recycle()
        return byteArray
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
