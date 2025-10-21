package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.CarGuestDto

interface ICarGuestRepository {
    suspend fun createGuest(guest: CarGuestDto)
    suspend fun updateGuest(guest: CarGuestDto)

    suspend fun getGuestByPlate(plate: String): CarGuestDto?
}