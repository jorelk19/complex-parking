package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IUserRepository
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.UserData
import com.complexparking.entities.toUserDto
import com.complexparking.utils.encryptionTools.RSAEncryptionHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateUserUseCase(
    private val userRepository: IUserRepository,
) : BaseUseCase<UserData, Boolean> {
    override suspend fun execute(params: UserData?): Flow<ResultUseCaseState<Boolean?>> = flow {
        emit(ResultUseCaseState.Loading)
        try {
            params?.let {
                val existingUser = userRepository.getUserByUserName(params.email)
                if (existingUser == null) {
                    val newUser = params.copy(
                        password = RSAEncryptionHelper.encryptText(it.password) ?: ""
                    )
                    userRepository.insertUser(newUser.toUserDto())
                    emit(ResultUseCaseState.Success(true))
                } else {
                    emit(ResultUseCaseState.Success(false))
                }
            } ?: run {
                emit(ResultUseCaseState.Success(false))
            }
        } catch (ex: Exception) {
            emit(ResultUseCaseState.Error(ex))
        }
    }
}