package com.complexparking.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.complexparking.R
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomContainer
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.CustomText3XLage
import com.complexparking.ui.base.CustomText6XLage
import com.complexparking.ui.base.CustomTextLage
import com.complexparking.ui.base.Dimensions
import com.complexparking.ui.base.Dimensions.size20dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size50dp
import com.complexparking.ui.base.EnumEditTextType
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.validateError
import java.util.Date
import org.koin.java.KoinJavaComponent.inject

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val homeScreenViewModel: HomeScreenViewModel by inject(HomeScreenViewModel::class.java)
    val homeModel by homeScreenViewModel.homeScreenModel
    val colors = LocalCustomColors.current

    CustomContainer(
        statusBarColor = colors.colorNeutralBg,

        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.home_screen_header_title),
                modifier = Modifier.fillMaxSize()
            )
        },
        body = {
            HomeBody(
                modifier = Modifier.fillMaxSize(),
                homeScreenModel = homeModel
            )
        }
    )
}

@Composable
fun HomeBody(
    modifier: Modifier,
    homeScreenModel: HomeScreenModel,
) {
    var plate by remember { mutableStateOf("") }
    plate = homeScreenModel.plateText
    val colors = LocalCustomColors.current
    Column(
        modifier = modifier
            .padding(start = size50dp, end = size50dp)
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier.height(size50dp)
        )
        CustomText6XLage(
            text = formatPlate(plate),
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier.height(size20dp)
        )
        CustomEditText(
            text = homeScreenModel.plateText,
            titleText = stringResource(id = R.string.home_screen_plate_title),
            imageStart = ImageVector.vectorResource(R.drawable.ic_plate_number),
            bottomText = validateError(
                hasError = homeScreenModel.plateError,
                errorType = homeScreenModel.plateErrorType
            ),
            onValueChange = {
                homeScreenModel.onTextPlateChange(it)
            },
            hasFocus = homeScreenModel.plateFocus,
            hasError = homeScreenModel.plateError,
            maxLength = 6
        )
        CustomEditText(
            text = homeScreenModel.unit,
            titleText = stringResource(id = R.string.home_screen_complex_unit_to_visit),
            imageStart = ImageVector.vectorResource(R.drawable.ic_house),
            bottomText = validateError(
                hasError = homeScreenModel.unitError,
                errorType = homeScreenModel.unitErrorType
            ),
            onValueChange = homeScreenModel.onTextUnitChange,
            hasError = homeScreenModel.unitError,
            typeText = EnumEditTextType.NUMBER,
            maxLength = 3
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(
                    width = Dimensions.borderStrokeMedium,
                    color = colors.colorStroke,
                    shape = RoundedCornerShape(Dimensions.cornerRadius)
                )
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .weight(0.5f)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTextLage(
                    text = stringResource(id = R.string.home_screen_plate_access_date),
                )
                Spacer(modifier = Modifier.height(size20dp))
                CustomText3XLage(
                    text = homeScreenModel.date
                )
            }
            VerticalDivider(
                color = colors.colorStroke,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
            )
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .weight(0.5f)
                    .padding(5.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTextLage(
                    text = stringResource(id = R.string.home_screen_plate_access_hour),
                )
                Spacer(modifier = Modifier.height(size20dp))
                CustomText3XLage(
                    text = homeScreenModel.hourArrive
                )
            }
        }
        Spacer(
            modifier = Modifier.height(size30dp)
        )
        CustomButton(
            onClick = {
                homeScreenModel.onRegisterButtonClick()
            },
            buttonText = stringResource(id = R.string.home_screen_button_register),
            isEnabled = homeScreenModel.isButtonRegisterEnabled
        )
    }
}

fun formatPlate(plate: String): String {
    return if (plate.length > 3) {
        val sb = StringBuilder(plate)
        sb.insert(3, "-")
        sb.toString().uppercase()
    } else {
        plate.uppercase()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        navController = rememberNavController()
    )
}

@Preview
@Composable
fun PreviewBodyScreen() {
    val currentDate = Date()
    HomeBody(
        modifier = Modifier,
        homeScreenModel = HomeScreenModel()
    )
}