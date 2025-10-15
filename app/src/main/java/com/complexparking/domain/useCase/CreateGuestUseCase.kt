package com.complexparking.domain.useCase

import android.util.Log
import com.complexparking.entities.Visitor
import com.complexparking.entities.visitorToDto
import com.complexparking.data.repository.local.GuestRepository
import com.complexparking.domain.interfaces.ICreateVisitorUnitUseCase

class CreateGuestUseCase(private val guestRepository: GuestRepository): ICreateVisitorUnitUseCase {
    override suspend fun createVisitor(visitor: Visitor): Boolean {
        return try {
            guestRepository.createGuest(visitor.visitorToDto())
            true
        }catch (exception: Exception){
            Log.d(CreateGuestUseCase::class.java.name, exception.message.toString())
            false
        }
    }
}