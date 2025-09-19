package com.complexparking.utils.tools

import android.content.Context
import java.io.InputStream
import org.apache.poi.ss.usermodel.WorkbookFactory

object ExcelTools {
    fun readExcelFileFromAssets(context: Context, fileName: String) {
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val workbook = WorkbookFactory.create(inputStream)
            val sheet = workbook.getSheetAt(0) // Get the first sheet

            for (row in sheet) {
                for (cell in row) {
                    when (cell.cellType) {
                        org.apache.poi.ss.usermodel.CellType.STRING -> {
                            val cellValue = cell.stringCellValue
                            // Process string value
                        }
                        org.apache.poi.ss.usermodel.CellType.NUMERIC -> {
                            val cellValue = cell.numericCellValue
                            // Process numeric value
                        }
                        // Handle other cell types as needed
                        else -> { /* Do nothing or handle unknown types */ }
                    }
                }
            }
            workbook.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}