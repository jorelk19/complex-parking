package com.complexparking.data.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.complexparking.data.repository.local.converters.Converters
import com.complexparking.data.repository.local.dao.BrandDao
import com.complexparking.data.repository.local.dao.CarDao
import com.complexparking.data.repository.local.dao.CarGuestDao
import com.complexparking.data.repository.local.dao.ComplexDao
import com.complexparking.data.repository.local.dao.DocumentTypeDao
import com.complexparking.data.repository.local.dao.ParkingConfigurationDao
import com.complexparking.data.repository.local.dao.PersonDao
import com.complexparking.data.repository.local.dao.ResidentDao
import com.complexparking.data.repository.local.dao.UserDao
import com.complexparking.data.repository.local.dto.BrandDto
import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.CarGuestDto
import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.DocumentTypeDto
import com.complexparking.data.repository.local.dto.ParkingConfigurationDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.complexparking.data.repository.local.dto.ResidentDto
import com.complexparking.data.repository.local.dto.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

@Database(
    entities = [
        BrandDto::class,
        DocumentTypeDto::class,
        CarDto::class,
        PersonDto::class,
        ResidentDto::class,
        CarGuestDto::class,
        ComplexDto::class,
        UserDto::class,
        ParkingConfigurationDto::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ParkingDatabase : RoomDatabase() {
    abstract val brandDao: BrandDao
    abstract val documentTypeDao: DocumentTypeDao
    abstract val residentDao: ResidentDao
    abstract val carGuestDao: CarGuestDao
    abstract val complexDao: ComplexDao
    abstract val personDao: PersonDao
    abstract val carDao: CarDao
    abstract val userDao: UserDao
    abstract val parkingConfigurationDao: ParkingConfigurationDao

    companion object {

        private const val DATABASE_NAME = "parking_database"

        /*private val phrase = SQLiteDatabase.getBytes(BuildConfig.DB_CONFIG.toCharArray())
        private val factory = SupportFactory(phrase)*/
        @Volatile
        private var INSTANCE: ParkingDatabase? = null

        /* fun getInstance(context: Context, scope: CoroutineScope): ParkingDatabase {
             val tempInstance = INSTANCE
             if (tempInstance != null) {
                 return tempInstance
             }

             synchronized(this) {
                 val newInstance = buildDatabase(context, scope)
                 return newInstance
             }
         }*/

        fun getInstance(context: Context): ParkingDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val newDb = Room.databaseBuilder(
                    context.applicationContext,
                    ParkingDatabase::class.java,
                    DATABASE_NAME
                )
                    /*.openHelperFactory(factory)*/
                    //.addCallback(CallbackDataBase(context))
                    .build()
                INSTANCE = newDb
                return newDb
            }
        }
    }
}