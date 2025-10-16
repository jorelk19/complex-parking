package com.complexparking.di

import com.complexparking.data.repository.local.ComplexRepository
import com.complexparking.data.repository.local.DocumentTypeRepository
import com.complexparking.data.repository.local.GuestRepository
import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.data.repository.local.IDocumentTypeRepository
import com.complexparking.data.repository.local.IGuestRepository
import com.complexparking.data.repository.local.IUserRepository
import com.complexparking.data.repository.local.ParkingDatabase
import com.complexparking.data.repository.local.UserRepository
import org.koin.dsl.module

fun databaseModule(db: ParkingDatabase) = module {
    single<IComplexRepository> { ComplexRepository(db) }
    single<IGuestRepository> { GuestRepository(db) }
    single<IDocumentTypeRepository> { DocumentTypeRepository(db) }
    single<IUserRepository> { UserRepository(db) }
}