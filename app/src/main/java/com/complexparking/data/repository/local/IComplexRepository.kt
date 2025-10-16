package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.complexparking.data.repository.local.dto.UserDto

interface IComplexRepository {
    suspend fun saveComplexInformation(userDto: UserDto, complexDto: ComplexDto, personList: List<PersonDto>)
}