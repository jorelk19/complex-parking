package com.complexparking.entities

import com.complexparking.data.repository.local.dto.CarGuestDto

fun CarGuest.guestToDto(): CarGuestDto {
    return CarGuestDto(
        date = this.date,
        hourStart = this.hourStart,
        hourEnd = this.hourEnd,
        isInComplex = this.isInComplex,
        house = this.house,
        plate = this.plate
    )
}