package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.PersonDto

class ComplexRepository(private val db: ParkingDatabase): IComplexRepository {
    override suspend fun saveComplexInformation(complexDto: ComplexDto, personList: List<PersonDto>) {
        db.complexDao.insertComplexInformation(complexDto, personList)
    }
}