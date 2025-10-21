package com.complexparking.ui.base

import androidx.lifecycle.ViewModel
import com.complexparking.R
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.ui.controls.SnackBarController
import com.complexparking.ui.controls.SnackBarEvents
import com.complexparking.ui.controls.SnackType
import com.complexparking.ui.utilities.LoadingManager

open class BaseViewModel: ViewModel() {
    suspend fun <T> validateUseCaseResult(useCaseResult: ResultUseCaseState<T>, resultAction: suspend (T) -> Unit) {
        when(useCaseResult) {
            is ResultUseCaseState.Loading -> {
                LoadingManager.showLoader()
            }
            is ResultUseCaseState.Success<T> -> {
                LoadingManager.hideLoader()
                resultAction(useCaseResult.data)
            }
            is ResultUseCaseState.Error -> {
                LoadingManager.hideLoader()
                SnackBarController.sendEvent(
                    event = SnackBarEvents(
                        titleId = R.string.app_error_process_use_case,
                        textMessage = useCaseResult.exception.message ?: "",
                        snackType = SnackType.ERROR,
                        buttonIconId = R.drawable.ic_close,
                        iconId = R.drawable.ic_error
                    )
                )
            }
        }
    }
}