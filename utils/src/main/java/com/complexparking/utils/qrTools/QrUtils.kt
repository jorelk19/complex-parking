package com.complexparking.utils.qrTools

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

/**
 * A utility object for generating QR codes.
 */
object QrUtils {

    /**
     * Generates a QR code bitmap from a given text.
     *
     * @param text The text content to encode into the QR code.
     * @param width The desired width of the QR code image in pixels.
     * @param height The desired height of the QR code image in pixels.
     * @return A [Bitmap] object representing the QR code, or null if generation fails.
     */
    fun generateQRCode(text: String, width: Int = 512, height: Int = 512): Bitmap? {
        // Return null if the text to encode is empty
        if (text.isBlank()) {
            return null
        }

        // Set up encoding hints for the QRCodeWriter
        val hints = mapOf(
            EncodeHintType.CHARACTER_SET to "UTF-8",
            EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H, // High error correction
            EncodeHintType.MARGIN to 1 // Margin size (1 means a thin border)
        )

        val writer = QRCodeWriter()

        return try {
            // Encode the text into a BitMatrix
            val bitMatrix = writer.encode(
                text,
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints
            )

            // Create a Bitmap from the BitMatrix
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    // Set pixel color based on the BitMatrix value
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            bmp
        } catch (e: Exception) {
            // Log the exception or handle it as needed
            e.printStackTrace()
            null
        }
    }
}
