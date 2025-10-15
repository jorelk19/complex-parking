package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.PersonDto

interface IComplexRepository {
    suspend fun saveComplexInformation(complexDto: ComplexDto, personList: List<PersonDto>)
}