package com.complexparking.di

import com.complexparking.data.repository.local.BrandRepository
import com.complexparking.data.repository.local.CarRepository
import com.complexparking.data.repository.local.ComplexRepository
import com.complexparking.data.repository.local.DocumentTypeRepository
import com.complexparking.data.repository.local.CarGuestRepository
import com.complexparking.data.repository.local.IBrandRepository
import com.complexparking.data.repository.local.ICarRepository
import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.data.repository.local.IDocumentTypeRepository
import com.complexparking.data.repository.local.ICarGuestRepository
import com.complexparking.data.repository.local.IParkingConfigurationRepository
import com.complexparking.data.repository.local.IUserRepository
import com.complexparking.data.repository.local.ParkingConfigurationRepository
import com.complexparking.data.repository.local.ParkingDatabase
import com.complexparking.data.repository.local.UserRepository
import org.koin.dsl.module

fun databaseModule(db: ParkingDatabase) = module {
    single<IComplexRepository> { ComplexRepository(db) }
    single<ICarGuestRepository> { CarGuestRepository(db) }
    single<IDocumentTypeRepository> { DocumentTypeRepository(db) }
    single<IUserRepository> { UserRepository(db) }
    single<ICarRepository> { CarRepository(db) }
    single<IBrandRepository> { BrandRepository(db) }
    single<IParkingConfigurationRepository> { ParkingConfigurationRepository(db) }
}