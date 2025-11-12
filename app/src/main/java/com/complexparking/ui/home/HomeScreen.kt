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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithoutScroll
import com.complexparking.ui.base.CustomButton
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
import com.complexparking.ui.base.TypeLifeCycle
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.utilities.formatPlate
import com.complexparking.ui.validateError
import com.complexparking.ui.widgets.CustomGeneralHeader
import com.complexparking.utils.pdfTools.generatePDF
import java.io.File
import java.util.Date
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val homeScreenViewModel: HomeScreenViewModel = koinViewModel()
    homeScreenViewModel.isCompletedLoadingData.collectAsStateWithLifecycle()
    val homeModel by homeScreenViewModel.homeScreenModel
    val colors = LocalCustomColors.current
    val context = LocalContext.current

    if (homeScreenViewModel.printFile.value) {
        generatePDF(context, getDirectory())
    }

    ContainerWithoutScroll(
        //observeLifeCycleAction = { homeScreenViewModel.observePrintState() },
        typeLifeCycleList = arrayListOf(TypeLifeCycle.ON_CREATE),
        header = {
            CustomGeneralHeader(
                headerTitle = stringResource(id = R.string.home_screen_header_title)
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
            .padding(start = size20dp, end = size20dp)
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier.height(size50dp)
        )
        CustomText6XLage(
            text = plate.formatPlate(),
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
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    homeScreenModel.onRegisterButtonClick()
                },
                buttonText = stringResource(id = R.string.home_screen_button_register),
                isEnabled = homeScreenModel.isButtonRegisterEnabled
            )
        }
    }
}

@Composable
private fun getDirectory(): File {
    val mediaDir = LocalContext.current.externalMediaDirs.firstOrNull()?.let {
        File(it, stringResource(R.string.app_name)).apply {
            mkdirs()
        }
    }
    return mediaDir ?: LocalContext.current.filesDir
}

@Preview(showBackground = true)
@Composable
fun PreviewBodyScreen() {
    val currentDate = Date()
    HomeBody(
        modifier = Modifier,
        homeScreenModel = HomeScreenModel()
    )
}