package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.PersonDto

@Dao
abstract class ComplexDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertComplexData(complexDto: ComplexDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPersonList(personList: List<PersonDto>)

    @Transaction
    open suspend fun insertComplexInformation(complexDto: ComplexDto, personList: List<PersonDto>) {
        insertComplexData(complexDto)
        insertPersonList(personList)
    }

}