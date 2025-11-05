package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.PersonDto

@Dao
abstract class ComplexDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertComplexData(complexDto: ComplexDto)

    @Update
    abstract suspend fun updateComplexData(complexDto: ComplexDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPersonList(personList: List<PersonDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCar(carDto: CarDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCarList(carList: List<CarDto>)

    @Query("SELECT complexUnits FROM complex")
    abstract suspend fun getComplexUnits(): Int

    @Query("SELECT * FROM complex")
    abstract suspend fun getComplexConfiguration(): List<ComplexDto>

    @Query("SELECT * FROM car WHERE plate = :plate")
    abstract suspend fun getCarByPlate(plate: String) : CarDto
}