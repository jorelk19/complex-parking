package com.complexparking.data.repository.local

import androidx.room.withTransaction
import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.ParkingConfigurationDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.complexparking.data.repository.local.dto.UserDto

class ComplexRepository(private val db: ParkingDatabase) : IComplexRepository {
    override suspend fun saveComplexInformation(
        userDto: UserDto,
        parkingData: ParkingConfigurationDto,
        complexDto: ComplexDto,
        personList: List<PersonDto>,
        carList: List<CarDto>
    ) {
        db.withTransaction {
            db.userDao.insertUser(userDto)
            db.parkingConfigurationDao.insertParkingConfiguration(parkingData)
            db.complexDao.insertComplexData(complexDto)
            db.complexDao.insertPersonList(personList)
            db.complexDao.insertCarList(carList)
        }
    }

    override suspend fun getComplexUnitQuantity(): Int {
        return db.complexDao.getComplexUnits()
    }
}