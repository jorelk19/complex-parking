package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.CarGuestDto
import java.util.Date

class CarGuestRepository(private val db: ParkingDatabase): ICarGuestRepository {
    override suspend fun createGuest(guest: CarGuestDto) {
        db.carGuestDao.insertCarGuest(guest)
    }

    override suspend fun updateGuest(guest: CarGuestDto) {
        db.carGuestDao.updateCarGuest(guest)
    }

    override suspend fun getGuestByPlate(plate: String): CarGuestDto? {
        return db.carGuestDao.getCarGuestByPlate(plate)
    }

    override suspend fun getGuestByDate(startDate: Date, endDate: Date, userName: String): List<CarGuestDto> {
        return db.carGuestDao.getCarGuestByDate(startDate, endDate, userName)
    }

    override suspend fun getAllGuest(): List<CarGuestDto> {
        return db.carGuestDao.getAllGuest()
    }
}