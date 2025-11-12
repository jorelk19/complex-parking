package com.complexparking.utils.printerTools

import android.graphics.Bitmap
import android.util.Log
import java.util.Locale
import androidx.core.graphics.get


object BitmapHelper {
    private val binaryArray = arrayOf<String?>(
        "0000", "0001", "0010", "0011",
        "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
        "1100", "1101", "1110", "1111"
    )

    fun decodeBitmap(bmp: Bitmap): ByteArray? {
        //Bitmap bmp = Bitmap.createScaledBitmap(bitmap, 50, 50, false);
        var bmp = bmp
        val maxWidth = 350

        var bmpWidth = bmp.getWidth()
        var bmpHeight = bmp.getHeight()

        if (bmpWidth > maxWidth) {
            val aspectRatio = bmp.getWidth() / bmp.getHeight().toFloat()
            bmpWidth = maxWidth
            bmpHeight = Math.round(bmpWidth / aspectRatio)
            bmp = Bitmap.createScaledBitmap(bmp, bmpWidth, bmpHeight, false)
        }

        val list: MutableList<String> = ArrayList<String>() //binaryString list
        var sb: StringBuffer?

        val zeroCount = bmpWidth % 8

        val zeroStr = StringBuilder()
        if (zeroCount > 0) {
            for (i in 0..<(8 - zeroCount)) {
                zeroStr.append("0")
            }
        }

        for (i in 0..<bmpHeight) {
            sb = StringBuffer()
            for (j in 0..<bmpWidth) {
                val color = bmp[j, i]

                val r = (color shr 16) and 0xff
                val g = (color shr 8) and 0xff
                val b = color and 0xff

                // if color close to white，bit='0', else bit='1'
                if (r > 160 && g > 160 && b > 160) sb.append("0")
                else sb.append("1")
            }
            if (zeroCount > 0) {
                sb.append(zeroStr)
            }
            list.add(sb.toString())
        }

        val bmpHexList = binaryListToHexStringList(list)
        val commandHexString = "1D763000"
        var widthHexString = Integer
            .toHexString(
                if (bmpWidth % 8 == 0)
                    bmpWidth / 8
                else
                    (bmpWidth / 8 + 1)
            )
        if (widthHexString.length > 2) {
            Log.e("decodeBitmap error", " width is too large")
            return null
        } else if (widthHexString.length == 1) {
            widthHexString = "0" + widthHexString
        }
        widthHexString = widthHexString + "00"

        var heightHexString = Integer.toHexString(bmpHeight)
        if (heightHexString.length > 2) {
            Log.e("decodeBitmap error", " height is too large")
            return null
        } else if (heightHexString.length == 1) {
            heightHexString = "0" + heightHexString
        }
        heightHexString = heightHexString + "00"

        val commandList: MutableList<String?> = ArrayList<String?>()
        commandList.add(commandHexString + widthHexString + heightHexString)
        commandList.addAll(bmpHexList)

        return hexList2Byte(commandList)
    }

    private fun binaryListToHexStringList(list: MutableList<String>): MutableList<String?> {
        val hexList: MutableList<String?> = ArrayList<String?>()
        for (binaryStr in list) {
            val sb = StringBuffer()
            var i = 0
            while (i < binaryStr.length) {
                val str = binaryStr.substring(i, i + 8)

                val hexString = myBinaryStrToHexString(str)
                sb.append(hexString)
                i += 8
            }
            hexList.add(sb.toString())
        }
        return hexList
    }

    private fun myBinaryStrToHexString(binaryStr: String): String {
        val hex = StringBuilder()
        val f4 = binaryStr.substring(0, 4)
        val b4 = binaryStr.substring(4, 8)
        val hexStr = "0123456789ABCDEF"
        for (i in binaryArray.indices) {
            if (f4 == binaryArray[i]) hex.append(hexStr.substring(i, i + 1))
        }
        for (i in binaryArray.indices) {
            if (b4 == binaryArray[i]) hex.append(hexStr.substring(i, i + 1))
        }

        return hex.toString()
    }

    private fun hexList2Byte(list: MutableList<String?>): ByteArray {
        val commandList: MutableList<ByteArray> = ArrayList<ByteArray>()
        for (hexStr in list) {
            commandList.add(hexStringToBytes(hexStr)!!)
        }
        return sysCopy(commandList)
    }

    private fun hexStringToBytes(hexString: String?): ByteArray? {
        var hexString = hexString
        if (hexString == null || hexString == "") {
            return null
        }
        hexString = hexString.uppercase(Locale.getDefault())
        val length = hexString.length / 2
        val hexChars = hexString.toCharArray()
        val d = ByteArray(length)
        for (i in 0..<length) {
            val pos = i * 2
            d[i] = (charToByte(hexChars[pos]).toInt() shl 4 or charToByte(hexChars[pos + 1]).toInt()).toByte()
        }
        return d
    }

    private fun sysCopy(srcArrays: MutableList<ByteArray>): ByteArray {
        var len = 0
        for (srcArray in srcArrays) {
            len += srcArray.size
        }
        val destArray = ByteArray(len)
        var destLen = 0
        for (srcArray in srcArrays) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray.size)
            destLen += srcArray.size
        }
        return destArray
    }

    private fun charToByte(c: Char): Byte {
        return "0123456789ABCDEF".indexOf(c).toByte()
    }
}