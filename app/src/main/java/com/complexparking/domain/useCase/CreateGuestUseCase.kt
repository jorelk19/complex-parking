package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IGuestRepository
import com.complexparking.domain.interfaces.ICreateGuestUnitUseCase
import com.complexparking.entities.Visitor
import com.complexparking.entities.visitorToDto

class CreateGuestUseCase(private val guestRepository: IGuestRepository) : ICreateGuestUnitUseCase {
    override suspend fun createVisitor(visitor: Visitor): Boolean {
        return try {
            guestRepository.createGuest(visitor.visitorToDto())
            true
        } catch (exception: Exception) {
            //Log.d(CreateGuestUseCase::class.java.name, exception.message.toString())
            false
        }
    }
}