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
    suspend fun insertDocumentType(documentTypeDto: DocumentTypeDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocumentTypeList(documentList: List<DocumentTypeDto>)

    @Query("SELECT * FROM documentType ORDER BY id")
    suspend fun getAllDocumentType(): List<DocumentTypeDto>
}