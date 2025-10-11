package com.complexparking.ui.controls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import com.complexparking.R
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.Dimensions
import com.complexparking.ui.base.Dimensions.size10dp
import kotlinx.coroutines.launch


@Composable
fun SnackBarControl(
    snackBarHostState: SnackbarHostState,
) {
    Row {
        val scope = rememberCoroutineScope()
        SnackbarHost(hostState = snackBarHostState) { snackbarData ->
            CustomSnackBar(
                drawableId = R.drawable.ic_launcher_foreground,
                title = "",
                message = "",
                containerColor = Color.Red,
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
    action: () -> Unit
) {
    Snackbar(
        containerColor = containerColor,
        modifier = Modifier.padding(Dimensions.size8dp).wrapContentSize()
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
                modifier = Modifier.clickable{
                    action()
                }.constrainAs(iconEnd) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Icon(
                    painterResource(buttonIconId),
                    contentDescription = null
                )
            }
        }
    }
}