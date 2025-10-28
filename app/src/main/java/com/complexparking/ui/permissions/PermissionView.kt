package com.complexparking.ui.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.complexparking.ui.navigation.AppScreens
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionView(navController: NavHostController) {
    val viewModel: PermissionsViewModel = koinViewModel()
    val onStart = viewModel.isCompletedLoadingData.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val hasPermissions = remember { mutableStateOf(false) }
    if (!viewModel.isGranted.value) {
        val launcherMultiplePermissions = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap ->
            val list = if (Build.VERSION.SDK_INT > 32) {
                permissionsMap.filter { permission ->
                    permission.key != "android.permission.WRITE_EXTERNAL_STORAGE" && permission.key != "android.permission.READ_EXTERNAL_STORAGE"
                }
            } else {
                permissionsMap
            }
            val areGranted = list.values.reduce { acc, next -> acc && next }
            viewModel.setPermissions(areGranted)
            navController.navigate(AppScreens.SPLASHSCREEN.route)
        }

        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
        )
        if (!viewModel.isGranted.value) {
            LaunchedEffect(key1 = Unit) {
                checkAndRequestPermissions(
                    context = context,
                    permissions = permissions,
                    launcher = launcherMultiplePermissions
                )
            }
        } else {
            navController.navigate(AppScreens.SPLASHSCREEN.route)
        }
    } else {
        navController.navigate(AppScreens.SPLASHSCREEN.route)
    }
}

private fun checkAndRequestPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
) {
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        // Use location because permissions are already granted
    } else {
        // Request permissions
        launcher.launch(permissions)
    }
}

object Permissions {
    private val permissionsGranted = mutableStateOf(false)

    fun setPermissionsState(areGranted: Boolean) {
        permissionsGranted.value = areGranted
    }

    fun arePermissionGranted(): Boolean {
        return permissionsGranted.value
    }
}