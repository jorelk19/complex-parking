package com.complexparking.domain.useCase

import com.complexparking.domain.interfaces.ILoginUseCase

class LoginUseCase: ILoginUseCase {
    override suspend fun validateUser(userName: String, password: String): LoginState {
        return if (userName.lowercase() == "jorelk19@gmail.com" && password == "Evan2017*") {
            LoginState.LoginSuccess
        } else {
            LoginState.LoginError
        }
    }
}