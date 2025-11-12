package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.CarGuestDto
import java.util.Date

interface ICarGuestRepository {
    suspend fun createGuest(guest: CarGuestDto)
    suspend fun updateGuest(guest: CarGuestDto)

    suspend fun getGuestByPlate(plate: String): CarGuestDto?
    suspend fun getGuestByDate(startDate: Date, endDate: Date, userName: String): List<CarGuestDto>
    suspend fun getAllGuest(): List<CarGuestDto>
}