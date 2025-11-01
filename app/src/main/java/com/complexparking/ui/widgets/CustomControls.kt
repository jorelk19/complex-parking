package com.complexparking.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.complexparking.R
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.Dimensions.size10dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.EnumEditTextType
import com.complexparking.ui.theme.LocalCustomColors

@Composable
fun ParkingParameterSection(
    parkingHourPrice: String,
    parkingMaxFreeHour: String,
    onParkingHourPriceChange: (String) -> Unit,
    onParkingMaxFreeHourChange: (String) -> Unit,
    parkingHourHasFocus: Boolean = false
) {
    val colors = LocalCustomColors.current
    Column {
        CustomEditText(
            text = parkingHourPrice,
            titleText = stringResource(id = R.string.parking_setting_screen_hour_price),
            onValueChange = { text ->
                onParkingHourPriceChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_timer),
            hasFocus = parkingHourHasFocus,
            bottomText = "",
            typeText = EnumEditTextType.NUMBER
        )
        CustomEditText(
            text = parkingMaxFreeHour,
            titleText = stringResource(id = R.string.parking_setting_screen_mx_free_hour),
            onValueChange = { text ->
                onParkingMaxFreeHourChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_clock),
            hasFocus = false,
            bottomText = "",
            typeText = EnumEditTextType.NUMBER
        )
    }
}

@Composable
fun CustomSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = LocalCustomColors.current

    Surface(
        modifier = modifier,
        shadowElevation = size5dp,
        /*border = BorderStroke(size2dp, colors.colorNeutralBorder),*/
        shape = RoundedCornerShape(size5dp),
        color = colors.colorNeutralBgWeak
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(size10dp)
        ) {
            content()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ParkingParameterSectionPreview() {
    ParkingParameterSection(
        "",
        "",
        {},
        {}
    )
}