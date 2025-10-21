package com.complexparking.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.complexparking.ui.controls.SnackBarControl
import com.complexparking.ui.navigation.bottomNavigationBar.BottomBarControl
import com.complexparking.ui.theme.ComplexParkingTheme
import com.complexparking.ui.theme.LocalCustomColors

@Composable
fun MainContainer(
    header: @Composable () -> Unit = {},
    body: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.height(50.dp)
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
                    }
                    .fillMaxSize()
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
            ) {
                footer()
            }
        }
    }
}

@Composable
fun CustomContainer(
    statusBarColor: Color = LocalCustomColors.current.colorPrimaryBg,
    bodyColor: Color = LocalCustomColors.current.colorPrimaryBg,
    header: @Composable () -> Unit = {},
    body: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
) {
    SystemBarColorManager(
        statusBarColorColor = statusBarColor
    )
    FlatContainer(
        statusBarColor = statusBarColor,
        bodyColor = bodyColor
    ) {
        Column {
            header()
        }
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (bodyContainer, footerContainer) = createRefs()
            ScrollContainer(
                modifier = Modifier.constrainAs(bodyContainer) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                Column {
                    body()
                }
            }
            Column(
                modifier = Modifier.constrainAs(footerContainer) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                footer()
            }
        }
    }
}

@Preview
@Composable
fun FlatContainer(
    statusBarColor: Color = LocalCustomColors.current.colorPrimaryBg,
    bodyColor: Color = LocalCustomColors.current.colorPrimaryBg,
    content: @Composable () -> Unit = {},
) {
    val colors = LocalCustomColors.current
    SystemBarColorManager(
        statusBarColorColor = statusBarColor
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = bodyColor)
    ) {
        content()
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
fun SimpleContainer(
    header: @Composable () -> Unit = {},
    body: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.height(50.dp)
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
            ) {
                footer()
            }
        }
    }
}

@Composable
fun BaseContainer(
    statusBarColor: Color = LocalCustomColors.current.colorPrimaryBg,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val colors = LocalCustomColors.current
    SystemBarColorManager(
        statusBarColorColor = statusBarColor
    )
    val snackbarHostState = remember { SnackbarHostState() }
    ComplexParkingTheme {
        Scaffold(
            snackbarHost = {
                SnackBarControl(snackbarHostState)
            },
            content = {
                Column(modifier = Modifier.padding(it)) {
                    content()
                }
            },
            bottomBar = { bottomBar() }
        )
    }
}