package com.complexparking.domain.interfaces

interface IValidateUnitUseCase {
    suspend fun validateUnit(unit: Int): Boolean
}