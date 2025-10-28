package com.complexparking.ui.permissions

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.complexparking.domain.useCase.GetPermissionsUseCase
import com.complexparking.domain.useCase.SetPermissionsUseCase
import com.complexparking.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class PermissionsViewModel(
    private val setPermissionsUseCase: SetPermissionsUseCase,
    private val getPermissionsUseCase: GetPermissionsUseCase
): BaseViewModel() {

    private val _isGranted = mutableStateOf(false)
    val isGranted get() = _isGranted

    override fun onStartScreen() {
        arePermissionsGranted()
    }

    fun setPermissions(isGranted: Boolean) {
        viewModelScope.launch {
            setPermissionsUseCase.execute(isGranted).collect {
            }
        }
    }

    fun arePermissionsGranted(){
        viewModelScope.launch {
            getPermissionsUseCase.execute(null).collect { resultUseCaseState ->
                validateUseCaseResult(resultUseCaseState) { result ->
                    result?.let {
                        _isGranted.value = result
                        //Permissions.setPermissionsState(result)
                    }
                }
            }
        }
    }
}