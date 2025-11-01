package com.complexparking.domain.useCase

import com.complexparking.data.repository.local.IUserRepository
import com.complexparking.domain.base.BaseUseCase
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.entities.LoginDataAccess
import com.complexparking.ui.utilities.json
import com.complexparking.utils.encryptionTools.RSAEncryptionHelper
import com.complexparking.utils.preferences.IS_ADMIN_USER
import com.complexparking.utils.preferences.StorePreferenceUtils
import com.complexparking.utils.preferences.USER_DATA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LoginUseCase(
    private val userRepository: IUserRepository,
    private val storePreferencesUtils: StorePreferenceUtils,
) : BaseUseCase<LoginDataAccess, Boolean> {
    override suspend fun execute(params: LoginDataAccess?): Flow<ResultUseCaseState<Boolean>> = flow {
        coroutineScope {
            emit(ResultUseCaseState.Loading)
            try {
                params?.let { loginDataAccess ->
                    val userData = withContext(Dispatchers.IO) { userRepository.getUserByUserName(loginDataAccess.user.lowercase()) }
                    userData?.let {
                        val pwdDecrypted = withContext(Dispatchers.IO) { RSAEncryptionHelper.decryptText(it.password) }
                        if (pwdDecrypted == loginDataAccess.password) {
                            storePreferencesUtils.putString(key = USER_DATA, value = it.json())
                            storePreferencesUtils.putBoolean(key = IS_ADMIN_USER, value = it.isAdmin)
                            emit(ResultUseCaseState.Success(true))
                        } else {
                            emit(ResultUseCaseState.Success(false))
                        }
                    } ?: run {
                        emit(ResultUseCaseState.Success(false))
                    }
                }
            } catch (ex: Exception) {
                emit(ResultUseCaseState.Error(ex))
            }
        }
    }
}