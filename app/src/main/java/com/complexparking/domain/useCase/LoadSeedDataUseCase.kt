package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IBrandRepository
import com.complexparking.data.repository.local.IDocumentTypeRepository
import com.complexparking.data.repository.local.getBrandList
import com.complexparking.data.repository.local.getDocumentList
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.utils.preferences.SEED_DATA_LOADED
import com.complexparking.utils.preferences.StorePreferenceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LoadSeedDataUseCase(
    private val brandRepository: IBrandRepository,
    private val documentTypeRepository: IDocumentTypeRepository,
    private val storePreferenceUtils: StorePreferenceUtils,
) : BaseUseCase<Any, Nothing> {
    override suspend fun execute(params: Any?): Flow<ResultUseCaseState<Nothing?>> = flow {
        try {
            val hasSeedData = storePreferenceUtils.getBoolean(SEED_DATA_LOADED, false)
            if (!hasSeedData) {
                withContext(Dispatchers.IO) {
                    val brandList = brandRepository.getBrandList()
                    if (brandList.isEmpty()) {
                        brandRepository.insertBrandList(getBrandList())
                    }
                    val documentList = documentTypeRepository.getDocumentTypeList()
                    if (documentList.isEmpty()) {
                        documentTypeRepository.insertDocumentTypeList(getDocumentList())
                    }
                    storePreferenceUtils.putBoolean(SEED_DATA_LOADED, true)
                }
            }
            emit(ResultUseCaseState.Success(null))
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}