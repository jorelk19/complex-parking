package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.CarVisitorDto

class RepositoryManager(private val db: ParkingDatabase) {
    suspend fun createVisitor(visitor: CarVisitorDto) {
        db.carVisitorDto.insertCarVisitor(visitor)
    }
}