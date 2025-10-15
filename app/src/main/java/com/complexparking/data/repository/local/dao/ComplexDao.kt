package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.PersonDto

@Dao
abstract class ComplexDao {
    @Upsert
    abstract suspend fun insertComplexData(complexDto: ComplexDto)

    @Upsert
    abstract suspend fun insertPersonList(personList: List<PersonDto>)

    @Transaction
    open suspend fun insertComplexInformation(complexDto: ComplexDto, personList: List<PersonDto>) {
        insertComplexData(complexDto)
        insertPersonList(personList)
    }

}