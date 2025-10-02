package com.complexparking.domain.interfaces

import com.complexparking.utils.tools.FileData

interface ILoadComplexUnitDataUseCase {
    suspend fun loadComplexData(fileDataList: ArrayList<FileData>)
}