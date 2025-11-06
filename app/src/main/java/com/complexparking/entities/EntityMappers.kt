package com.complexparking.entities

import com.complexparking.data.repository.local.dto.BrandDto
import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.CarGuestDto
import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.UserDto

fun CarGuest.toGuestDto(): CarGuestDto {
    return CarGuestDto(
        date = this.date,
        hourStart = this.hourStart,
        hourEnd = this.hourEnd,
        isInComplex = this.isInComplex,
        house = this.house,
        plate = this.plate
    )
}

fun UserData.toUserDto() = UserDto(
    id = this.id,
    name = this.name,
    userName = this.email,
    password = this.password,
    date = this.creationDate,
    isAdmin = this.isAdmin
)

fun Car.toCarDto(brandDto: BrandDto) = CarDto(
    color = this.color,
    brand = brandDto,
    plate = this.plate,
    unit = this.unit
)

fun ComplexData.toComplexDto(): ComplexDto {
    return ComplexDto(
        id = this.id,
        complexName = this.complexName,
        complexUnits = this.complexUnit,
        complexAddress = this.complexAddress,
        quantityParking = this.parkingQuantity,
        adminName = this.adminName,
        maxFreeHour = this.parkingMaxFreeHour,
        parkingPrice = this.parkingPrice
    )
}

fun ComplexConfiguration.toComplexDto(): ComplexDto {
    return ComplexDto(
        id = this.id,
        complexName = this.complexName,
        complexUnits = this.complexUnits,
        complexAddress = this.complexAddress,
        quantityParking = this.complexQuantityParking,
        adminName = this.adminName,
        maxFreeHour = this.maxFreeHour,
        parkingPrice = this.parkingPrice
    )
}

