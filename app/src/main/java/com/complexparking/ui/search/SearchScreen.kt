package com.complexparking.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithoutScroll
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size50dp
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.validateError
import com.complexparking.ui.widgets.CustomGeneralHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier,
) {
    val searchScreenViewModel: SearchScreenViewModel = koinViewModel()
    val searchModel by searchScreenViewModel.searchScreenModel
    val colors = LocalCustomColors.current
    val context = LocalContext.current

    ContainerWithoutScroll(
        modifier = modifier,
        header = {
            CustomGeneralHeader(
                headerTitle = stringResource(id = R.string.search_screen_guest_title)
            )
            /*CustomHeader(
                headerTitle = stringResource(id = R.string.search_screen_guest_title),
                modifier = Modifier.fillMaxSize()
            )*/
        },
        body = {
            SearchBody(
                searchScreenModel = searchModel
            )
        }
    )
}

@Composable
fun SearchBody(searchScreenModel: SearchScreenModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = size50dp, end = size50dp)
    ) {
        CustomEditText(
            text = searchScreenModel.plateText,
            titleText = stringResource(id = R.string.home_screen_plate_title),
            imageStart = ImageVector.vectorResource(R.drawable.ic_plate_number),
            bottomText = validateError(
                hasError = searchScreenModel.plateError,
                errorType = searchScreenModel.plateErrorType
            ),
            onValueChange = {
                searchScreenModel.onTextPlateChange(it)
            },
            hasFocus = searchScreenModel.plateFocus,
            hasError = searchScreenModel.plateError,
            maxLength = 6
        )
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
                    searchScreenModel.onSearchButtonClick()
                },
                buttonText = stringResource(id = R.string.search_screen_button_text),
                isEnabled = searchScreenModel.isButtonSearchEnabled
            )
        }
    }
}

@Preview
@Composable
fun SearchPreview() {
    SearchBody(SearchScreenModel())
}