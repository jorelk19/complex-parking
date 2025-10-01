package com.complexparking.utils.tools

import android.content.Context
import android.net.Uri
import java.io.BufferedReader
import java.io.InputStreamReader

object ExcelTools {
    fun readExcelFile(context: Context, filePath: Uri): ArrayList<FileData> {
        val dataList = arrayListOf<FileData>()
        val rawList = arrayListOf<String>()
        return try {
            //val documentsPath = Environment.getExternalStoragePublicDirectory(null).absolutePath
            val inputStream = context.contentResolver.openInputStream(filePath)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            var hasRows = true
            while (hasRows) {
                if(reader.readLine().also { line = it } != null) {
                    rawList.add(line.toString())
                } else {
                    hasRows = false
                }
            }
            val fileContent = stringBuilder.toString()
            // Process the fileContent here (e.g., display in a TextView)
            println("File Content: $fileContent")
            reader.close()
            inputStream?.close()
            var lineNumber = 1
            rawList.forEach { line ->
                if (lineNumber > 1) {
                    dataList.add(mapData(line))
                } else {
                    lineNumber++
                }
            }

            dataList
        } catch (e: Exception) {
            e.printStackTrace()
            dataList
        }
    }

    private fun mapData(line: String): FileData {
        val fields = line.split(";")
        if (fields.size != 10) {
            throw Exception("Numero de columnas en el archivo son incorrectas")
        }
        return FileData(
            unit = fields[0],
            cellphone = fields[1],
            name = fields[2],
            lastName = fields[3],
            identification = fields[4],
            documentType = fields[5],
            plate = fields[6],
            model = fields[7],
            brand = fields[8],
            color = fields[9]
        )
    }
}