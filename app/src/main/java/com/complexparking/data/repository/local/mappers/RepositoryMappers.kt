package com.complexparking.data.repository.local.mappers

import com.complexparking.data.repository.local.dto.BrandDto
import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.ParkingConfigurationDto
import com.complexparking.data.repository.local.dto.UserDto
import com.complexparking.entities.Brand
import com.complexparking.entities.Car
import com.complexparking.entities.ParkingConfiguration
import com.complexparking.entities.UserData

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

fun UserDto.toUserData() = UserData(
    email = this.userName,
    name = this.name,
    isAdmin = this.isAdmin,
    password = this.password,
    creationDate = this.date
)

fun ParkingConfigurationDto.toParkingConfiguration() = ParkingConfiguration(
    id = this.id,
    parkingPrice = this.parkingPrice,
    maxFreeHour = this.maxFreeHour
)