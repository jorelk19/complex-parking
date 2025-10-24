package com.complexparking.domain.base

sealed class ResultUseCaseState<out T> {
    data class Success<out T>(val data: T) : ResultUseCaseState<T>()
    data class Error(val exception: Throwable) : ResultUseCaseState<Nothing>()
    object Loading : ResultUseCaseState<Nothing>()
}