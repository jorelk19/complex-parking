package com.complexparking.ui.controls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import com.complexparking.R
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.Dimensions
import com.complexparking.ui.base.Dimensions.size10dp
import com.complexparking.ui.base.Dimensions.size20dp
import com.complexparking.ui.base.Dimensions.size60dp
import kotlinx.coroutines.launch


@Composable
fun SnackBarControl(
    snackBarHostState: SnackbarHostState,
) {
    Row {
        val scope = rememberCoroutineScope()
        SnackbarHost(hostState = snackBarHostState) { snackbarData ->
            CustomSnackBar(
                drawableId = SnackBarController.snackData.value.iconId,
                title = if (SnackBarController.snackData.value.titleId != 0) {
                    stringResource(SnackBarController.snackData.value.titleId)
                } else {
                    stringResource(R.string.app_error_generic_title_use_case)
                },
                message = if(SnackBarController.snackData.value.messageId != 0) {
                    stringResource(SnackBarController.snackData.value.messageId)
                } else SnackBarController.snackData.value.textMessage.ifEmpty {
                    stringResource(R.string.app_error_generic_message_use_case)
                },
                containerColor = Color.Blue,
                buttonIconId = SnackBarController.snackData.value.buttonIconId,
                action = {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    SnackBarController.snackData.value.action()
                }
            )
        }
        ObserveAsEvents(
            flow = SnackBarController.events,
            key1 = snackBarHostState
        ) {
            scope.launch {
                snackBarHostState.currentSnackbarData?.dismiss()
                snackBarHostState.showSnackbar(
                    message = "",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}

@Composable
private fun CustomSnackBar(
    drawableId: Int,
    title: String,
    message: String,
    containerColor: Color,
    buttonIconId: Int,
    action: () -> Unit,
) {
    Snackbar(
        containerColor = containerColor,
        modifier = Modifier
            .padding(Dimensions.size8dp)
            .fillMaxWidth()
            .height(size60dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (iconStart, textContainer, iconEnd) = createRefs()
            Column(
                modifier = Modifier.constrainAs(iconStart) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Icon(
                    painterResource(drawableId),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = size10dp, end = size10dp)
                    .wrapContentSize()
                    .constrainAs(textContainer) {
                        start.linkTo(iconStart.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(iconEnd.start)
                        width = fillToConstraints
                    }
            ) {
                CustomTextMedium(title, modifier = Modifier.fillMaxWidth())
                CustomTextMedium(message, modifier = Modifier.fillMaxWidth())
            }
            Column(
                modifier = Modifier
                    .clickable {
                        action()
                    }
                    .constrainAs(iconEnd) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Icon(
                    modifier = Modifier.size(size20dp),
                    painter = painterResource(buttonIconId),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SnackBarControlPreview() {
    CustomSnackBar(
        drawableId = R.drawable.ic_circle_check,
        title = "Titulo",
        message = "Subtitulo",
        containerColor = Color.Blue,
        buttonIconId = R.drawable.ic_close,
        action = {}
    )
}