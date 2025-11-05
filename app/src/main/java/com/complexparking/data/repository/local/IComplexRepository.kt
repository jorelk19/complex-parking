package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.complexparking.data.repository.local.dto.UserDto

interface IComplexRepository {
    suspend fun saveComplexInformation(
        userDto: UserDto,
        complexDto: ComplexDto,
        personList: List<PersonDto>,
        carList: List<CarDto>
    )
    suspend fun getComplexUnitQuantity(): Int

    suspend fun getComplexConfiguration(): ComplexDto

    suspend fun updateComplexConfiguration(complexDto: ComplexDto)
}