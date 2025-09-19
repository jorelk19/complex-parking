package com.complexparking.domain.useCase

sealed class LoginState {
    object LoginSuccess: LoginState()
    object LoginError: LoginState()
}