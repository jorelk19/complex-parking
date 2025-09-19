package com.complexparking.di

import com.complexparking.data.repository.local.ParkingDatabase
import com.complexparking.data.repository.local.RepositoryManager
import org.koin.dsl.module

fun databaseModule(db: ParkingDatabase) = module {
    single { RepositoryManager(db) }
}