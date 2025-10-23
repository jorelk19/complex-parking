package com.complexparking.data.repository.local

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.complexparking.data.repository.local.dto.BrandDto
import com.complexparking.data.repository.local.dto.DocumentTypeDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CallbackDataBase(
    private val context: Context,
) : RoomDatabase.Callback() {

    private lateinit var documentList: List<DocumentTypeDto>
    private lateinit var brandList: List<BrandDto>

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        documentList = getDocumentList()
        brandList = getBrandList()
        val applicationScope = CoroutineScope(Dispatchers.IO)

        val dataBase = ParkingDatabase.getInstance(context)
        val documentTypeDao = dataBase.documentTypeDao
        val brandDao = dataBase.brandDao
        /*applicationScope.launch(Dispatchers.IO) {*/
        ioThread {
            applicationScope.launch {
                withContext(Dispatchers.IO) {
                    /* async {*/
                    documentList.forEach {
                        documentTypeDao.insertDocumentType(it)
                    }
                    /*}.await()*/
                    /*async {*/
                    brandList.forEach {
                        brandDao.insertBrand(it)
                    }
                }
            }
            /*}.await()*/
        }
        /*val applicationScope = CoroutineScope(Dispatchers.IO)
        val db = ParkingDatabase.getInstance(context)
        val documentTypeDao = db.documentTypeDao
        val brandDao = db.brandDao
        applicationScope.launch {
            async { documentTypeDao.insertDocumentTypeList(getDocumentList()) }.await()
            async { brandDao.insertBrandList(getBrandList()) }.await()
        }*/
    }
}