package com.complexparking.data.repository.local.dao

import androidx.room.Dao

@Dao
interface CarDao {
    fun insertCar()
}