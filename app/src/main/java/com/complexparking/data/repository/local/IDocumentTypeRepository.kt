package com.complexparking.data.repository.local

import com.complexparking.data.repository.local.dto.DocumentTypeDto

interface IDocumentTypeRepository {
    suspend fun getDocumentTypeList(): List<DocumentTypeDto>
    suspend fun insertDocumentTypeList(documentList: List<DocumentTypeDto>)
}