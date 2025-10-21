package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IBrandRepository
import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.data.repository.local.IDocumentTypeRepository
import com.complexparking.data.repository.local.dto.BrandDto
import com.complexparking.data.repository.local.dto.CarDto
import com.complexparking.data.repository.local.dto.DocumentTypeDto
import com.complexparking.data.repository.local.dto.PersonDto
import com.complexparking.data.repository.local.dto.UserDto
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.ComplexData
import com.complexparking.entities.toComplexDto
import java.util.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadComplexUnitDataUseCase(
    private val complexRepository: IComplexRepository,
    private val documentTypeRepository: IDocumentTypeRepository,
    private val brandRepository: IBrandRepository,
) : BaseUseCase<ComplexData, Boolean> {
    var documentTypeListCache: List<DocumentTypeDto> = arrayListOf()
    var brandList: List<BrandDto> = arrayListOf()
    override suspend fun execute(params: ComplexData?): Flow<ResultUseCaseState<Boolean>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            params?.let {
                complexRepository.saveComplexInformation(
                    userDto = UserDto(
                        userName = params.adminEmail,
                        name = params.adminName,
                        date = Date(),
                        password = params.adminPassword,
                        isAdmin = true
                    ),
                    complexDto = params.toComplexDto(),
                    personList = params.fileDataList.map {
                        PersonDto(
                            name = it.name,
                            lastName = it.lastName,
                            document = it.document,
                            documentType = getDocumentType(it.documentType),
                            house = it.unit,
                            cellNumber = it.cellphone
                        )
                    },
                    carList = params.fileDataList.map {
                        CarDto(
                            color = it.color,
                            brand = extractBrand(it.brand),
                            plate = it.plate,
                            unit = it.unit
                        )
                    }
                )
                emit(ResultUseCaseState.Success(true))
            } ?: run {
                emit(ResultUseCaseState.Success(false))
            }
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }

    private suspend fun getDocumentType(docType: String): DocumentTypeDto {
        if (documentTypeListCache.isEmpty()) {
            documentTypeListCache = documentTypeRepository.getDocumentTypeList()
        }
        return documentTypeListCache.first { it.name.uppercase() == docType.uppercase() }
    }

    private suspend fun extractBrand(brandName: String): BrandDto {
        if (brandList.isEmpty()) {
            brandList = brandRepository.getBrandList()
        }
        return brandList.first { it.name == brandName.uppercase() }
    }
}



