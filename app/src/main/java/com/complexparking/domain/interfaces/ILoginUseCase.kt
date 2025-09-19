package com.complexparking.domain.interfaces

import com.complexparking.domain.useCase.LoginState

interface ILoginUseCase {
    suspend fun validateUser(userName: String, password: String): LoginState
}