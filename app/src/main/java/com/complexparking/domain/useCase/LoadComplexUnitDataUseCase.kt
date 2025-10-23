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
import com.complexparking.utils.preferences.IS_WIZARD_COMPLETED
import com.complexparking.utils.preferences.StorePreferenceUtils
import java.util.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.w3c.dom.DocumentType

class LoadComplexUnitDataUseCase(
    private val complexRepository: IComplexRepository,
    private val documentTypeRepository: IDocumentTypeRepository,
    private val brandRepository: IBrandRepository,
    private val storePreferenceUtils: StorePreferenceUtils,
) : BaseUseCase<ComplexData, Boolean> {
    var documentTypeListCache: List<DocumentTypeDto> = arrayListOf()
    var brandList: List<BrandDto> = arrayListOf()
    override suspend fun execute(params: ComplexData?): Flow<ResultUseCaseState<Boolean>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            params?.let {
                val personList = arrayListOf<PersonDto>()
                it.fileDataList.forEach { fileData ->
                    personList.add(
                        PersonDto(
                            name = fileData.name,
                            lastName = fileData.lastName,
                            document = fileData.document,
                            documentType = getDocumentType(fileData.documentType),
                            house = fileData.unit,
                            cellNumber = fileData.cellphone
                        )
                    )
                }
                val carList = it.fileDataList.map {
                    CarDto(
                        color = it.color,
                        brand = extractBrand(it.brand),
                        plate = it.plate,
                        unit = it.unit
                    )
                }
                complexRepository.saveComplexInformation(
                    userDto = UserDto(
                        userName = params.adminEmail,
                        name = params.adminName,
                        date = Date(),
                        password = params.adminPassword,
                        isAdmin = true
                    ),
                    complexDto = params.toComplexDto(),
                    personList = personList,
                    carList = carList
                )
                storePreferenceUtils.putBoolean(IS_WIZARD_COMPLETED, true)
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
        return documentTypeListCache.firstOrNull { it.name.uppercase() == docType.uppercase() } ?: documentTypeListCache.first { it.name == "CC" }
    }

    private suspend fun extractBrand(brandName: String): BrandDto {
        if (brandList.isEmpty()) {
            brandList = brandRepository.getBrandList()
        }
        return brandList.first { it.name == brandName.uppercase() }
    }
}



