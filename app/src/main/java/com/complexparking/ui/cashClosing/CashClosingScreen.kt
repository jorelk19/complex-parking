package com.complexparking.ui.cashClosing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithoutScroll
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.Dimensions.size20dp
import com.complexparking.ui.widgets.CustomGeneralHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun CashClosingScreen(
    navController: NavController,
) {
    val cashClosingScreenViewModel: CashClosingScreenViewModel = koinViewModel()
    val cashClosingState by cashClosingScreenViewModel.cashClosingState.collectAsStateWithLifecycle()
    cashClosingScreenViewModel.isCompletedLoadingData.collectAsStateWithLifecycle()
    CashClosingScreenContainer(
        cashClosingState = cashClosingState,
        onClickCashClosingButton = { cashClosingScreenViewModel.onCashClosingAction() }
    )
}

@Composable
private fun CashClosingScreenContainer(
    cashClosingState: CashClosingState,
    onClickCashClosingButton: () -> Unit,
) {
    ContainerWithoutScroll(
        header = {
            CustomGeneralHeader(
                headerTitle = stringResource(R.string.cash_closing_screen_title)
            )
        },
        body = {
            CashClosingScreenBody(
                uiState = cashClosingState,
                onClickCashClosingButton = onClickCashClosingButton,
                {},
                {},
                {},
                formatCurrency = { "" }
            )
        }
    )
}

@Composable
private fun CashClosingScreenBody(
    uiState: CashClosingState,
    onClickCashClosingButton: () -> Unit,
    onActualCashCountedChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onSubmit: () -> Unit,
    formatCurrency: (Double) -> String,
) {
    Column(
        modifier = Modifier
            .padding(start = size20dp, end = size20dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Make the column scrollable
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Summary Section ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow("Starting Cash:", formatCurrency(uiState.startingCash))
                    InfoRow("Total System Sales:", formatCurrency(uiState.totalSales))
                    InfoRow("Cash Payments Received:", formatCurrency(uiState.cashPayments))
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    InfoRow(
                        label = "Expected Cash in Drawer:",
                        value = formatCurrency(uiState.expectedCash),
                        isBold = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- User Input Section ---
            Text("Enter Counted Amounts", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Actual Cash Counted - User Input
            OutlinedTextField(
                value = uiState.actualCashCounted,
                onValueChange = onActualCashCountedChange,
                label = { Text("Actual Cash Counted") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Text("$", style = MaterialTheme.typography.bodyLarge) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Difference Display ---
            val differenceColor = when {
                uiState.difference > 0 -> Color(0xFF008000) // Green for over
                uiState.difference < 0 -> MaterialTheme.colorScheme.error // Red for short
                else -> Color.Gray
            }
            InfoRow(
                label = "Difference (Over/Short):",
                value = formatCurrency(uiState.difference),
                valueColor = differenceColor,
                isBold = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Notes Section
            OutlinedTextField(
                value = uiState.notes,
                onValueChange = onNotesChange,
                label = { Text("Notes (optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CustomButton(
                    buttonText = stringResource(R.string.cash_closing_screen_cash_close_button),
                    onClick = { onClickCashClosingButton() }
                )
            }
        }
    }
}

/**
 * A helper composable to create a consistent row for displaying information.
 */
@Composable
private fun InfoRow(
    label: String,
    value: String,
    isBold: Boolean = false,
    valueColor: Color = Color.Unspecified,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = value,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = valueColor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CashClosingScreenBodyPreview() {
    CashClosingScreenBody(
        CashClosingState(),
        {},
        {},
        {},
        {},
        { "" }
    )
}