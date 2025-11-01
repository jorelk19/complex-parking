package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.ParkingConfigurationDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.complexparking.data.repository.local.dto.UserDto

interface IComplexRepository {
    suspend fun saveComplexInformation(
        userDto: UserDto,
        parkingData: ParkingConfigurationDto,
        complexDto: ComplexDto,
        personList: List<PersonDto>,
        carList: List<CarDto>
    )
    suspend fun getComplexUnitQuantity(): Int
}