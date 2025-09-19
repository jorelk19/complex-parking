package com.complexparking.domain.useCase

import android.util.Log
import com.complexparking.entities.Visitor
import com.complexparking.entities.visitorToDto
import com.complexparking.data.repository.local.RepositoryManager
import com.complexparking.domain.interfaces.ICreateVisitorUnitUseCase

class CreateVisitorUseCase(private val repositoryManager: RepositoryManager): ICreateVisitorUnitUseCase {
    override suspend fun createVisitor(visitor: Visitor): Boolean {
        return try {
            repositoryManager.createVisitor(visitor.visitorToDto())
            true
        }catch (exception: Exception){
            Log.d(CreateVisitorUseCase::class.java.name, exception.message.toString())
            false
        }
    }
}