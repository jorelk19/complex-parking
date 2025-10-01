package com.complexparking.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionView() {
    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )

    LaunchedEffect(key1 = Unit) {
        permissions.launchMultiplePermissionRequest()
        Permissions.setPermissions(permissions.allPermissionsGranted)
    }
}

object Permissions {
    private val hasPermissions = mutableStateOf(false)

    fun setPermissions(granted: Boolean) {
        hasPermissions.value = granted
    }

    fun arePermissionGranted(): Boolean {
        return hasPermissions.value
    }
}