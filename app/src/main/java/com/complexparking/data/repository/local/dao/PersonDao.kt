package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.PersonDto

@Dao
interface PersonDao {
    @Upsert
    fun insertPerson(personDto: PersonDto)
}