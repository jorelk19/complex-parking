package com.complexparking.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.complexparking.ui.base.Dimensions.size10dp
import com.complexparking.ui.base.Dimensions.size50dp
import com.complexparking.ui.controls.SnackBarControl
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.utilities.LoadingManager
import com.complexparking.ui.utilities.PulseLoader

@Composable
fun ContainerWithScroll(
    bodyColor: Color = LocalCustomColors.current.colorPrimaryBg,
    header: @Composable () -> Unit = {},
    body: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
) {
    if (LoadingManager.loaderState().value) {
        PulseLoader()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bodyColor)
        ) {
            Column(
                modifier = Modifier
                    .height(size50dp)
                    .fillMaxWidth()
            ) {
                header()
            }
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (bodyContainer, footerContainer) = createRefs()
                ScrollContainer(
                    modifier = Modifier
                        .constrainAs(bodyContainer) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(footerContainer.top)
                        }
                ) {
                    Column {
                        body()
                    }
                }
                Column(
                    modifier = Modifier
                        .constrainAs(footerContainer) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .background(LocalCustomColors.current.colorPrimaryBg)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    footer()
                }
            }
        }
    }
}

@Composable
private fun ScrollContainer(modifier: Modifier, content: @Composable () -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        content()
    }
}

@Preview
@Composable
fun ContainerWithoutScroll(
    modifier: Modifier = Modifier,
    bodyColor: Color = LocalCustomColors.current.colorPrimaryBg,
    header: @Composable () -> Unit = {},
    body: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
) {
    /*Scaffold(
        topBar = header,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = bodyColor)
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier.height(50.dp)
                ) {
                    header()
                }
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val (bodyContainer, footerContainer) = createRefs()
                    Column(
                        modifier = Modifier
                            .constrainAs(bodyContainer) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .fillMaxSize()
                    ) {
                        body()
                    }
                    Column(
                        modifier = Modifier
                            .constrainAs(footerContainer) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                            .fillMaxWidth()
                    ) {
                        footer()
                    }
                }
            }
        }
    )*/
    if (LoadingManager.loaderState().value) {
        PulseLoader()
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = bodyColor)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                header()
            }
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (bodyContainer, footerContainer) = createRefs()
                Column(
                    modifier = Modifier
                        .constrainAs(bodyContainer) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxSize()
                ) {
                    body()
                }
                Column(
                    modifier = Modifier
                        .constrainAs(footerContainer) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .fillMaxWidth()
                        .padding(start = size10dp, end = size10dp)
                ) {
                    footer()
                }
            }
        }
    }
}

@Composable
fun BaseContainer(
    bottomBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navigationBarInsets = WindowInsets.navigationBars
    val density = LocalDensity.current

    // Calculate the height of the system navigation bar
    val navBarHeight = with(density) { navigationBarInsets.getBottom(density).toDp() }

    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 0.dp, top = 0.dp),
        snackbarHost = {
            SnackBarControl(snackbarHostState)
        },
        content = { innerPadding ->
            if (LoadingManager.loaderState().value) {
                PulseLoader()
            } else {
                Column(
                    Modifier
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                        .padding(innerPadding)
                ) {
                    content()
                }
            }
        },
        bottomBar = {
            bottomBar()
        }
    )
}

@Preview
@Composable
fun FlatContainer(
    modifier: Modifier = Modifier,
    bodyColor: Color = LocalCustomColors.current.colorPrimaryBg,
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = bodyColor)
    ) {
        content()
    }
}