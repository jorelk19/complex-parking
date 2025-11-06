package com.complexparking.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.complexparking.R
import com.complexparking.ui.base.CustomText2XLageBold
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.CustomTextMediumBold
import com.complexparking.ui.base.Dimensions.size10dp
import com.complexparking.ui.base.Dimensions.size24dp
import com.complexparking.ui.base.Dimensions.size2dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size35dp
import com.complexparking.ui.base.Dimensions.size40dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.controls.CustomImage
import com.complexparking.ui.theme.LocalCustomColors
import org.koin.androidx.compose.koinViewModel

@Composable
fun CustomGeneralHeader(
    headerTitle: String = "",
    imageStart: ImageVector? = null,
    imageEnd: ImageVector? = null,
    onClickStart: () -> Unit = {},
    onClickEnd: () -> Unit = {},
) {
    val viewModel: HeaderViewModel = koinViewModel()
    val isCompleted by viewModel.isCompletedLoadingData.collectAsStateWithLifecycle()
    val uiState by viewModel.headerState.collectAsStateWithLifecycle()
    CustomGeneralHeaderContainer(
        uiState = uiState,
        headerTitle = headerTitle,
        imageStart = imageStart,
        imageEnd = imageEnd,
        onClickStart = onClickStart,
        onClickEnd = onClickEnd
    )
}

@Composable
private fun CustomGeneralHeaderContainer(
    uiState: HeaderState,
    imageStart: ImageVector? = null,
    imageEnd: ImageVector? = null,
    onClickStart: () -> Unit = {},
    onClickEnd: () -> Unit = {},
    headerTitle: String,
) {
    val colors = LocalCustomColors.current
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(top = size10dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (imageS, textContainer, imageE) = createRefs()
                imageStart?.let { img ->
                    Box(
                        modifier = Modifier
                            .size(size24dp)
                            .constrainAs(imageS) {
                                start.linkTo(parent.start, margin = size10dp)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = img,
                            contentDescription = null,
                            modifier = Modifier.clickable { onClickStart() },
                            tint = colors.colorIcPrimary
                        )
                    }
                }
                ConstraintLayout(
                    modifier = Modifier
                        .constrainAs(textContainer) {
                            imageStart?.let {
                                start.linkTo(imageS.end)
                            } ?: run {
                                start.linkTo(parent.start)
                            }
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            imageEnd?.let {
                                end.linkTo(imageE.start)
                            } ?: {
                                end.linkTo(parent.end)
                            }
                            width = Dimension.fillToConstraints
                        }.fillMaxWidth()
                ) {
                    val (profileImg, titleContainer) = createRefs()
                    CustomCard(
                        imageId = R.drawable.ic_profile,
                        cardModifier = Modifier
                            .size(size35dp)
                            .constrainAs(profileImg) {
                                start.linkTo(parent.start, margin = size10dp)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                    Column(
                        modifier = Modifier.constrainAs(titleContainer) {
                            start.linkTo(profileImg.end, margin = size10dp)
                            top.linkTo(parent.
                            top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                    ) {
                        CustomTextMediumBold(stringResource(R.string.home_screen_header_profile_greetings, uiState.userData.name))
                        Spacer(modifier = Modifier.width(size5dp))
                        CustomTextMedium(
                            text = if (uiState.userData.isAdmin) {
                                stringResource(R.string.home_screen_header_profile_admin)
                            } else {
                                stringResource(R.string.home_screen_header_profile_security_guard)
                            }
                        )
                    }
                }
                imageEnd?.let { img ->
                    Box(
                        modifier = Modifier
                            .size(size24dp)
                            .constrainAs(imageE) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end, margin = size10dp)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageVector = img,
                            contentDescription = null,
                            modifier = Modifier.clickable { onClickEnd() }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(size10dp))
            if (headerTitle.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CustomText2XLageBold(
                        text = headerTitle,
                        color = colors.colorPrimaryText,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        HorizontalDivider(modifier = Modifier.height(size2dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CustomGeneralHeaderPreview() {
    CustomGeneralHeaderContainer(
        uiState = HeaderState(),
        headerTitle = "Prueba titulo"
    )
}