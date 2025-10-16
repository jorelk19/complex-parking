package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.data.repository.local.IDocumentTypeRepository
import com.complexparking.data.repository.local.IUserRepository
import com.complexparking.data.repository.local.dto.DocumentTypeDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.complexparking.data.repository.local.dto.UserDto
import com.complexparking.domain.interfaces.ILoadComplexUnitDataUseCase
import com.complexparking.entities.ComplexData
import com.complexparking.entities.toComplexDto
import com.complexparking.utils.tools.FileData
import java.time.LocalDate
import java.util.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.String

class LoadComplexUnitDataUseCase(
    private val complexRepository: IComplexRepository,
    private val documentTypeRepository: IDocumentTypeRepository
) : ILoadComplexUnitDataUseCase {
    var documentTypeListCache: List<DocumentTypeDto> = arrayListOf()

    override suspend fun saveComplexData(
        fileDataList: ArrayList<FileData>,
        complexData: ComplexData,
    ) {
        complexRepository.saveComplexInformation(
            userDto = UserDto(
                userName = complexData.adminEmail,
                name = complexData.adminName,
                date = Date(),
                password = complexData.adminPassword,
                isAdmin = true
            ),
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



