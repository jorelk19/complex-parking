package com.complexparking.entities

import com.complexparking.data.repository.local.dto.CarVisitorDto

fun Visitor.visitorToDto(): CarVisitorDto {
    return CarVisitorDto(
        date = this.date,
        hourStart = this.hourStart,
        hourEnd = this.hourEnd,
        isInComplex = this.isInComplex,
        house = this.house,
        plate = this.plate
    )
}