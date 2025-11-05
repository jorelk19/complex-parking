package com.complexparking.entities

import com.complexparking.utils.excelTools.FileData

data class ComplexData(
    val id: Int = 0,
    val complexUnit: Int,
    val complexName: String,
    val complexAddress: String,
    val parkingQuantity: Int,
    val parkingPrice: Double,
    val parkingMaxFreeHour: Int,
    val adminName: String,
    val adminEmail: String,
    val adminPassword: String,
    val fileDataList: ArrayList<FileData>,
)
