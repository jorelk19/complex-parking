package com.complexparking.entities

import com.complexparking.data.repository.local.dto.BrandDto
import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.CarGuestDto
import com.complexparking.data.repository.local.dto.ParkingConfigurationDto
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

fun ParkingConfiguration.toParkingConfigurationDto() = ParkingConfigurationDto(
    maxFreeHour = this.maxFreeHour,
    parkingPrice = this.parkingPrice
)
