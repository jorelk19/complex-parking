package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.CarGuestDto

class GuestRepository(private val db: ParkingDatabase): IGuestRepository {
    override suspend fun createGuest(guest: CarGuestDto) {
        db.carGuestDao.insertCarGuest(guest)
    }
}