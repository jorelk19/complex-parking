package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.BrandDto
import com.complexparking.data.repository.local.dto.DocumentTypeDto

fun getBrandList(): List<BrandDto> {
    val arrayList = arrayListOf<BrandDto>()
    arrayList.add(
        BrandDto(
            name = "HYUNDAI"
        )
    )
    arrayList.add(
        BrandDto(
            name = "CHEVROLET"
        )
    )
    arrayList.add(
        BrandDto(
            name = "KIA"
        )
    )
    arrayList.add(
        BrandDto(
            name = "NISSAN"
        )
    )
    arrayList.add(
        BrandDto(
            name = "FORD"
        )
    )
    arrayList.add(
        BrandDto(
            name = "TOYOTA"
        )
    )
    arrayList.add(
        BrandDto(
            name = "BMW"
        )
    )
    arrayList.add(
        BrandDto(
            name = "VOLKSWAGEN"
        )
    )
    arrayList.add(
        BrandDto(
            name = "DODGE"
        )
    )
    arrayList.add(
        BrandDto(
            name = "MERCEDES BENZ"
        )
    )
    arrayList.add(
        BrandDto(
            name = "BYD"
        )
    )
    arrayList.add(
        BrandDto(
            name = "HONDA"
        )
    )
    arrayList.add(
        BrandDto(
            name = "JETOUR"
        )
    )
    arrayList.add(
        BrandDto(
            name = "FOTON"
        )
    )
    arrayList.add(
        BrandDto(
            name = "SSANGYONG"
        )
    )
    arrayList.add(
        BrandDto(
            name = "JAC"
        )
    )
    return arrayList
}

fun getDocumentList(): List<DocumentTypeDto> {
    val arrayList = arrayListOf<DocumentTypeDto>()
    arrayList.add(
        DocumentTypeDto(
            name = "CC",
            description = "Cedula de ciudadania"
        )
    )
    arrayList.add(
        DocumentTypeDto(
            name = "CE",
            description = "Cedula de extranjeria"
        )
    )
    return arrayList
}