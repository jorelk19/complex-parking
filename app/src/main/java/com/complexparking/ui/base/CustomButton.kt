package com.complexparking.ui.base

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.complexparking.ui.theme.ColorGray1
import com.complexparking.ui.theme.LocalCustomColors

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String,
    isEnabled: Boolean = true
) {
    val colors = LocalCustomColors.current
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(colors.colorBgButton),
        enabled = isEnabled
    ) {
        CustomTextSmall(
            text = buttonText,
            color = ColorGray1,
            textAlign = TextAlign.Center
        )
    }
}