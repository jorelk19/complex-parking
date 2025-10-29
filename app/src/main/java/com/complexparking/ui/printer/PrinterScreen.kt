package com.complexparking.ui.printer

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomTextLage
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.CustomTextSmall
import com.complexparking.ui.navigation.AppScreens
import com.complexparking.utils.printerTools.PrinterData
import org.koin.androidx.compose.koinViewModel

@SuppressLint("MissingPermission")
@Composable
fun PrinterScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: PrinterViewModel = koinViewModel()

    val uiState by viewModel.uiState.collectAsState()
    var showDeviceDialog by remember { mutableStateOf(false) }

    // Permission launcher
    /*    val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.values.all { it }) {
                // All permissions granted, refresh devices
                viewModel.getPairedDevices()
            } else {
                // Handle permission denial
            }
        }*/

    // Effect to request permissions when the screen is first displayed
    /*   LaunchedEffect(Unit) {
           permissionLauncher.launch(viewModel.requiredPermissions)
       }*/
    BackHandler(enabled = true) {
        navController.navigate(AppScreens.SETTINGSCREEN.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomTextSmall("Bluetooth Printer Util")

        // Connection Status
        CustomTextMedium(
            text = "Status: ${uiState.connectionStatus}",
            color = if (uiState.isConnected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold
        )

        if (uiState.isConnecting) {
            CircularProgressIndicator()
        }

        // --- Buttons ---
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CustomButton(
                onClick = {
                    if (viewModel.isBluetoothEnabled()) {
                        // Inform user to enable Bluetooth
                        viewModel.getPairedDevices() // Refresh list
                        showDeviceDialog = true
                    }
                },
                buttonText = "Seleccionar impresora"
            )

            CustomButton(
                buttonText = "Prueba impresión",
                onClick = {
                    viewModel.printMessage(
                        printerData = PrinterData(
                            plate = "WWW-123",
                            qr = null,
                            date = "01/01/2025",
                            complexName = "Conjunto prueba"
                        )
                    )
                },
                isEnabled = uiState.isConnected // Only enable if connected
            )
        }

        CustomButton(
            onClick = { viewModel.disconnect() },
            isEnabled = uiState.isConnected,
            buttonText = "Desconectado"
        )
    }

    // --- Device Selection Dialog ---
    if (showDeviceDialog) {
        DeviceSelectionDialog(
            devices = uiState.pairedDevices,
            onDismiss = { showDeviceDialog = false },
            onDeviceSelected = { device ->
                viewModel.connectToDevice(device.address)
                showDeviceDialog = false
            }
        )
    }
}

@SuppressLint("MissingPermission")
@Composable
fun DeviceSelectionDialog(
    devices: List<android.bluetooth.BluetoothDevice>,
    onDismiss: () -> Unit,
    onDeviceSelected: (android.bluetooth.BluetoothDevice) -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth(0.9f)) {
            Column {
                CustomTextLage(
                    text = "Select a Paired Printer",
                    modifier = Modifier.padding(16.dp)
                )
                LazyColumn {
                    if (devices.isEmpty()) {
                        item {
                            CustomTextMedium(
                                text = "No paired devices found.",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else {
                        items(devices) { device ->
                            CustomTextMedium(
                                text = "${device.name}\n${device.address}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onDeviceSelected(device) }
                                    .padding(16.dp)
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}
