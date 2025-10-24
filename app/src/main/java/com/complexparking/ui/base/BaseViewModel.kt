package com.complexparking.ui.base

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.complexparking.R
import com.complexparking.domain.base.ResultUseCaseState
import com.complexparking.ui.controls.SnackBarController
import com.complexparking.ui.controls.SnackBarEvents
import com.complexparking.ui.controls.SnackType
import com.complexparking.ui.utilities.LoadingManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel: ViewModel() {

    abstract fun onStartScreen()
    private val _isCompletedLoadingData = MutableStateFlow(false)
    val isCompletedLoadingData = _isCompletedLoadingData
        .onStart {
            onStartScreen()
            _isCompletedLoadingData.value = true
            LoadingManager.hideLoader()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )
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