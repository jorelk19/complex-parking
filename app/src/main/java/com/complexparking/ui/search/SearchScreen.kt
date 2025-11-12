package com.complexparking.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toString
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithoutScroll
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.CustomText6XLage
import com.complexparking.ui.base.CustomTextLage
import com.complexparking.ui.base.CustomTextLageBold
import com.complexparking.ui.base.Dimensions.size10dp
import com.complexparking.ui.base.Dimensions.size20dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size50dp
import com.complexparking.ui.utilities.formatPlate
import com.complexparking.ui.validateError
import com.complexparking.ui.widgets.CustomGeneralHeader
import com.complexparking.ui.widgets.CustomSurface
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier,
) {
    val searchScreenViewModel: SearchScreenViewModel = koinViewModel()
    val searchModel by searchScreenViewModel.searchScreenModel.collectAsStateWithLifecycle()
    searchScreenViewModel.isCompletedLoadingData.collectAsStateWithLifecycle()

    ContainerWithoutScroll(
        modifier = modifier,
        header = {
            CustomGeneralHeader(
                headerTitle = stringResource(id = R.string.search_screen_guest_title)
            )
        },
        body = {
            SearchBody(
                searchScreenModel = searchModel,
                onSearchButtonClick = { searchScreenViewModel.onSearchButtonClick() },
                onTextPlateChange = { searchScreenViewModel.onTextPlateChange(it) }
            )
        }
    )
}

@Composable
fun SearchBody(
    searchScreenModel: SearchScreenModel,
    onSearchButtonClick: () -> Unit,
    onTextPlateChange: (String) -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = size50dp, end = size50dp)
    ) {
        var plate by remember { mutableStateOf("") }
        plate = searchScreenModel.plateText
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
            text = searchScreenModel.plateText,
            titleText = stringResource(id = R.string.home_screen_plate_title),
            imageStart = ImageVector.vectorResource(R.drawable.ic_plate_number),
            bottomText = validateError(
                hasError = searchScreenModel.plateError,
                errorType = searchScreenModel.plateErrorType
            ),
            onValueChange = {
                onTextPlateChange(it)
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
                    onSearchButtonClick()
                },
                buttonText = stringResource(id = R.string.search_screen_button_text),
                isEnabled = searchScreenModel.isButtonSearchEnabled
            )
        }
        Spacer(
            modifier = Modifier.height(size30dp)
        )
        CustomSurface {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (totalTile, unitTitle, timeTitle, total, unit, time) = createRefs()
                CustomTextLageBold(
                    text = stringResource(R.string.search_screen_total_to_pay),
                    modifier = Modifier.constrainAs(totalTile) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                )
                CustomTextLage(
                    text = searchScreenModel.totalToPay.toString(),
                    modifier = Modifier.constrainAs(total) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                )
                CustomTextLageBold(
                    text = stringResource(R.string.search_screen_unit_visited),
                    modifier = Modifier.constrainAs(unitTitle) {
                        top.linkTo(totalTile.bottom)
                        start.linkTo(parent.start)
                    }
                )
                CustomTextLage(
                    text = searchScreenModel.unitVisited.toString(),
                    modifier = Modifier.constrainAs(unit) {
                        end.linkTo(parent.end)
                        top.linkTo(total.bottom)
                    }
                )
                CustomTextLageBold(
                    text = stringResource(R.string.search_screen_billed_time),
                    modifier = Modifier.constrainAs(timeTitle) {
                        top.linkTo(unitTitle.bottom)
                        start.linkTo(parent.start)
                    }
                )
                CustomTextLage(
                    text = searchScreenModel.billedTime.toString(),
                    modifier = Modifier.constrainAs(time) {
                        end.linkTo(parent.end)
                        top.linkTo(unit.bottom)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    SearchBody(
        SearchScreenModel(),
        {},
        {}
    )
}