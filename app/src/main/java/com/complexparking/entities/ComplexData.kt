package com.complexparking.entities

import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.utils.excelTools.FileData
import kotlin.String

data class ComplexData(
    val complexUnit: Int,
    val complexName: String,
    val complexAddress: String,
    val parkingQuantity: Int,
    val parkingPrice: Double,
    val parkingMaxFreeHour: Int,
    val adminName: String,
    val adminEmail: String,
    val adminPassword: String,
    val fileDataList: ArrayList<FileData>
)
fun ComplexData.toComplexDto(): ComplexDto {
    return ComplexDto(
        complexName = this.complexName,
        complexUnits = this.complexUnit,
        complexAddress = this.complexAddress,
        quantityParking = this.parkingQuantity
    )
}