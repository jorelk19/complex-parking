package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IComplexRepository
import com.complexparking.data.repository.local.IUserRepository
import com.complexparking.data.repository.local.dto.UserDto
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.ComplexConfiguration
import com.complexparking.entities.toComplexDto
import com.complexparking.ui.utilities.fromJson
import com.complexparking.ui.utilities.json
import com.complexparking.utils.preferences.StorePreferenceUtils
import com.complexparking.utils.preferences.USER_DATA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UpdateParkingConfigurationUseCase(
    private val complexRepository: IComplexRepository,
    private val userRepository: IUserRepository,
    private val storePreferenceUtils: StorePreferenceUtils
) : BaseUseCase<ComplexConfiguration, Boolean> {
    override suspend fun execute(params: ComplexConfiguration?): Flow<ResultUseCaseState<Boolean>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            params?.let { configuration ->
                withContext(Dispatchers.IO) {
                    val complexConfiguration = complexRepository.getComplexConfiguration()
                    val newConfiguration = configuration.copy(id = complexConfiguration.id)
                    complexRepository.updateComplexConfiguration(complexDto = newConfiguration.toComplexDto())
                    val currentUserData = storePreferenceUtils.getString(USER_DATA, "")
                    currentUserData?.let {
                        val userDto = it.fromJson<UserDto>()
                        val userData = userDto.copy(name = newConfiguration.adminName)
                        userRepository.updateUserName(userData)
                        storePreferenceUtils.putString(USER_DATA, userData.json())
                    }
                }
                emit(ResultUseCaseState.Success(true))
            }
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}