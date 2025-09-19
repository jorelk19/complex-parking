package com.complexparking.data.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.complexparking.data.repository.local.dao.ResidentDao
import com.complexparking.data.repository.local.dto.BrandDto
import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.CarVisitorDto
import com.complexparking.data.repository.local.dto.DocumentTypeDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.complexparking.data.repository.local.dto.ResidentDto
import androidx.room.Room
import androidx.room.TypeConverters
import com.complexparking.data.repository.local.dao.BrandDao
import com.complexparking.data.repository.local.dao.CarDao
import com.complexparking.data.repository.local.dao.CarVisitorDao
import com.complexparking.data.repository.local.dao.DocumentTypeDao
import com.complexparking.data.repository.local.dao.PersonDao

@Database(
    entities = [
        /*BrandDto::class,
        DocumentTypeDto::class,
        CarDto::class,
        PersonDto::class,
        ResidentDto::class,*/
        CarVisitorDto::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ParkingDatabase : RoomDatabase() {
    /*abstract val residentDao: ResidentDao
    abstract val personDao: PersonDao
    abstract val documentTypeDao: DocumentTypeDao*/
    abstract val carVisitorDto: CarVisitorDao
    /*abstract val carDao: CarDao
    abstract val brandDao: BrandDao*/

    companion object {
        @Volatile
        private var INSTANCE: ParkingDatabase? = null

        fun getDatabase(context: Context): ParkingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParkingDatabase::class.java,
                    "parking_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}