package com.complexparking.ui.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.complexparking.ui.base.Dimensions.borderRadius4XLarge
import com.complexparking.ui.base.Dimensions.size150dp
import com.complexparking.ui.base.Dimensions.size1dp
import com.complexparking.ui.base.Dimensions.size2dp
import com.complexparking.ui.base.Dimensions.size48dp
import com.complexparking.ui.base.Dimensions.size8dp
import com.complexparking.ui.theme.LocalCustomColors

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String,
    isEnabled: Boolean = true,
) {
    val colors = LocalCustomColors.current
    Button(
        onClick = { onClick() },
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(size8dp),
        border = BorderStroke(size1dp, colors.colorNeutralBgWeak),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.colorPrimaryBgDefault,
            contentColor = colors.colorNeutralBg,
            disabledContainerColor = colors.colorNeutralBgDefault,
            disabledContentColor = colors.colorPrimaryBgDefault,

        )
    ) {
        CustomTextSmall(
            text = buttonText,
            color = colors.colorPrimaryTextButtonDefault,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    Column {
        CustomButton(
            modifier = Modifier
                .height(size48dp)
                .width(size150dp),
            onClick = {},
            buttonText = "Prueba de boton",
            isEnabled = true
        )
        CustomButton(
            modifier = Modifier
                .height(size48dp)
                .width(size150dp),
            onClick = {},
            buttonText = "Prueba de boton",
            isEnabled = false
        )
    }
}