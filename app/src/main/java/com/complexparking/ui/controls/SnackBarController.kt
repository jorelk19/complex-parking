package com.complexparking.ui.controls

import androidx.compose.runtime.mutableStateOf
import com.complexparking.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


enum class SnackType(value: String) {
    ERROR("ERROR"),
    INFO("INFO"),
    WARNING("WARNING")
}
data class SnackBarEvents(
    val titleId: Int = 0,
    val messageId: Int = 0,
    val textMessage: String = "",
    val iconId: Int = R.drawable.ic_email,
    val buttonIconId: Int = R.drawable.ic_padlock,
    val snackType: SnackType = SnackType.ERROR,
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