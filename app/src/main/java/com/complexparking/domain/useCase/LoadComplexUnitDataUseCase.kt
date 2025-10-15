package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.data.repository.local.IDocumentTypeRepository
import com.complexparking.data.repository.local.dto.DocumentTypeDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.complexparking.domain.interfaces.ILoadComplexUnitDataUseCase
import com.complexparking.entities.ComplexData
import com.complexparking.entities.toComplexDto
import com.complexparking.utils.tools.FileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.String

class LoadComplexUnitDataUseCase(
    private val complexRepository: IComplexRepository,
    private val documentTypeRepository: IDocumentTypeRepository
) : ILoadComplexUnitDataUseCase {
    lateinit var documentTypeListCache: List<DocumentTypeDto>

    override suspend fun saveComplexData(
        fileDataList: ArrayList<FileData>,
        complexData: ComplexData,
    ) {
        complexRepository.saveComplexInformation(
            complexDto = complexData.toComplexDto(),
            personList = fileDataList.map {
                PersonDto(
                    name = it.name,
                    lastName = it.lastName,
                    document = it.document,
                    documentType = getDocumentType(it.documentType),
                    house = it.unit,
                    cellNumber = it.cellphone
                )
            }
        )
    }

    private suspend fun getDocumentType(docType: String): DocumentTypeDto {
        if (documentTypeListCache.isEmpty()) {
            documentTypeListCache = documentTypeRepository.getDocumentTypeList()
        }
        return documentTypeListCache.first { it.name.uppercase() == docType.uppercase() }
    }
}



