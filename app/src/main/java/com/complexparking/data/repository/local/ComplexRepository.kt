package com.complexparking.data.repository.local

import androidx.room.withTransaction
import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.complexparking.data.repository.local.dto.UserDto

class ComplexRepository(private val db: ParkingDatabase): IComplexRepository {
    override suspend fun saveComplexInformation(
        userDto: UserDto,
        complexDto: ComplexDto,
        personList: List<PersonDto>
    ) {
        db.withTransaction {
            db.userDao.insertUser(userDto)
            db.complexDao.insertComplexData(complexDto)
            db.complexDao.insertPersonList(personList)
        }
    }
}