package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.CarDto

class CarRepository(
    private val db: ParkingDatabase
): ICarRepository {
    override suspend fun getCarByPlate(plate: String): CarDto? {
        return db.carDao.getCarByPlate(plate)
    }
}