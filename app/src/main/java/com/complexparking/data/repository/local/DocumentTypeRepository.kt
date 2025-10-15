package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.DocumentTypeDto

class DocumentTypeRepository(private val db: ParkingDatabase): IDocumentTypeRepository {
    override suspend fun getDocumentTypeList(): List<DocumentTypeDto> {
        return db.documentTypeDao.getAllDocumentType()
    }

    override suspend fun insertDocumentTypeList(documentList: List<DocumentTypeDto>)  {
        db.documentTypeDao.insertDocumentTypeList(documentList)
    }
}