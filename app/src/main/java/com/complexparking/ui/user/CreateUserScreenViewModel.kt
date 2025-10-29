package com.complexparking.ui.user

import com.complexparking.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateUserScreenViewModel: BaseViewModel() {
    private val _createUserState = MutableStateFlow(CreateUserState())
    val createUserState get() = _createUserState.asStateFlow()

    override fun onStartScreen() {
        _createUserState.value = CreateUserState()
    }
}