package com.complexparking.ui.controls

import androidx.compose.runtime.mutableStateOf
import com.complexparking.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackBarEvents(
    val titleId: Int = R.string.wizard_user_creation_admin_repeat_password,
    val subTitleId: Int = R.string.wizard_user_creation_user_repeat_password,
    val iconId: Int = R.drawable.ic_email,
    val buttonIconId: Int = R.drawable.ic_padlock,
    val action: () -> Unit = {}
)

object SnackBarController{
    private val _events = Channel<SnackBarEvents>()
    val events = _events.receiveAsFlow()

    private val _snackData = mutableStateOf(SnackBarEvents())
    val snackData get() = _snackData

    suspend fun sendEvent(event: SnackBarEvents) {
        _snackData.value = event
        _events.send(event)
    }
}