package com.complexparking.domain.interfaces

import com.complexparking.entities.Visitor

interface ICreateVisitorUnitUseCase {
    suspend fun createVisitor(visitor: Visitor): Boolean
}