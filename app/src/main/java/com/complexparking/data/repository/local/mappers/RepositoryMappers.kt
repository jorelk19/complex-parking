package com.complexparking.data.repository.local.mappers

import com.complexparking.data.repository.local.dto.BrandDto
import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.entities.Brand
import com.complexparking.entities.Car

fun CarDto.toCar() = Car(
    id = this.id,
    plate = this.plate,
    unit = this.unit,
    color = this.color,
    brand = this.brand.toBrand()
)

fun BrandDto.toBrand() = Brand(
    id = this.id,
    name = this.name
)