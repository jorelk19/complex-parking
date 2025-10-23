package com.complexparking.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.complexparking.data.repository.local.dto.DocumentTypeDto

@Dao
interface DocumentTypeDao {
    @Upsert
    fun insertDocumentType(documentTypeDto: DocumentTypeDto)

    @Upsert
    fun insertDocumentTypeList(documentList: List<DocumentTypeDto>)

    @Query("SELECT * FROM documentType ORDER BY id")
    suspend fun getAllDocumentType(): List<DocumentTypeDto>
}