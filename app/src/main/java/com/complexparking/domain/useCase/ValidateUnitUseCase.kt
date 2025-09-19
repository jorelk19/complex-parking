package com.complexparking.domain.useCase

import com.complexparking.domain.interfaces.IValidateUnitUseCase

class ValidateUnitUseCase : IValidateUnitUseCase {
    companion object {
        const val MAX_UNIT_COMPLEX = "MAX_UNIT_COMPLEX"
    }

    override suspend fun validateUnit(unit: Int): Boolean {
        val unitParameter = 106
        return unit in 1..unitParameter
    }
}