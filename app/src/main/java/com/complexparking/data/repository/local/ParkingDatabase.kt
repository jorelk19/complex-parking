package com.complexparking.data.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.complexparking.BuildConfig
import com.complexparking.data.repository.local.converters.Converters
import com.complexparking.data.repository.local.dao.BrandDao
import com.complexparking.data.repository.local.dao.CarDao
import com.complexparking.data.repository.local.dao.CarGuestDao
import com.complexparking.data.repository.local.dao.ComplexDao
import com.complexparking.data.repository.local.dao.DocumentTypeDao
import com.complexparking.data.repository.local.dao.PersonDao
import com.complexparking.data.repository.local.dao.ResidentDao
import com.complexparking.data.repository.local.dao.UserDao
import com.complexparking.data.repository.local.dto.BrandDto
import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.CarGuestDto
import com.complexparking.data.repository.local.dto.ComplexDto
import com.complexparking.data.repository.local.dto.DocumentTypeDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.complexparking.data.repository.local.dto.ResidentDto
import com.complexparking.data.repository.local.dto.UserDto
import kotlinx.coroutines.runBlocking
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [
        BrandDto::class,
        DocumentTypeDto::class,
        CarDto::class,
        PersonDto::class,
        ResidentDto::class,
        CarGuestDto::class,
        ComplexDto::class,
        UserDto::class
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

    companion object {

        private val phrase = SQLiteDatabase.getBytes(BuildConfig.DB_CONFIG.toCharArray())
        private val factory = SupportFactory(phrase)
        @Volatile
        private var INSTANCE: ParkingDatabase? = null

        fun getInstance(context: Context): ParkingDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ParkingDatabase::class.java,
                "parking_database"
            )
                .addCallback(seedDatabaseCallback(context))
                .openHelperFactory(factory)
                .build()

        private fun seedDatabaseCallback(context: Context): RoomDatabase.Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    ioThread {
                        val documentTypeDao = getInstance(context).documentTypeDao
                        val brandDao = getInstance(context).brandDao
                        runBlocking {
                            documentTypeDao.insertDocumentTypeList(documentList = getDocumentList())
                            brandDao.insertBrandList(getBrandList())
                        }
                    }
                }
            }
        }
    }
}