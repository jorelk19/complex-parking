package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.dto.UserDto
import com.complexparking.data.repository.local.mappers.toUserData
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.UserData
import com.complexparking.ui.utilities.fromJson
import com.complexparking.utils.preferences.StorePreferenceUtils
import com.complexparking.utils.preferences.USER_DATA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserDataUseCase(
    private val storePreferenceUtils: StorePreferenceUtils,
) : BaseUseCase<Any, UserData?> {
    override suspend fun execute(params: Any?): Flow<ResultUseCaseState<UserData?>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            val user = storePreferenceUtils.getString(USER_DATA, "")
            user?.let {
                val userDto = it.fromJson<UserDto>()
                val userData = userDto.toUserData()
                emit(ResultUseCaseState.Success(userData))
            } ?: run {
                emit(ResultUseCaseState.Success(null))
            }
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}