package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.CarDto

interface ICarRepository {
    suspend fun getCarByPlate(plate: String): CarDto?
}