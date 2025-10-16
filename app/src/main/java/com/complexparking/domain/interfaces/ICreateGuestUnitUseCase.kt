package com.complexparking.domain.interfaces

import com.complexparking.entities.Visitor

interface ICreateGuestUnitUseCase {
    suspend fun createVisitor(visitor: Visitor): Boolean
}