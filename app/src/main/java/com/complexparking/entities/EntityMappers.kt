package com.complexparking.entities

import com.complexparking.data.repository.local.dto.CarGuestDto
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