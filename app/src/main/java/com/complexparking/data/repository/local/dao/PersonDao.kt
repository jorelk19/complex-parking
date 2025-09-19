package com.complexparking.data.repository.local.dao

import androidx.room.Dao

@Dao
interface PersonDao {
    fun insertPerson()
}