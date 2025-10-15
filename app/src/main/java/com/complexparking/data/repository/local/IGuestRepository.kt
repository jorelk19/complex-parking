package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.CarGuestDto

interface IGuestRepository {
    suspend fun createGuest(guest: CarGuestDto)
}