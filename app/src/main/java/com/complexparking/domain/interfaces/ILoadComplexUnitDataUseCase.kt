package com.complexparking.domain.interfaces

import com.complexparking.entities.ComplexData
import com.complexparking.utils.tools.FileData

interface ILoadComplexUnitDataUseCase {
    suspend fun saveComplexData(fileDataList: ArrayList<FileData>, complexData: ComplexData)
}