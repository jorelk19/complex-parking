package com.complexparking.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.complexparking.R
import com.complexparking.ui.base.Dimensions.size100dp
import com.complexparking.ui.base.Dimensions.size10dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size50dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.controls.CustomImage
import com.complexparking.ui.controls.SnackBarControl
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.utilities.LoadingManager
import com.complexparking.ui.utilities.ObservableScreen
import com.complexparking.ui.utilities.PulseLoader
import com.complexparking.ui.widgets.CustomCard
import kotlinx.coroutines.launch

@Composable
fun ContainerWithScroll(
    bodyColor: Color = LocalCustomColors.current.colorPrimaryBg,
    header: @Composable () -> Unit = {},
    body: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    observeLifeCycleAction: () -> Unit = {},
    typeLifeCycleList: ArrayList<TypeLifeCycle> = arrayListOf(),
) {
    SetObserversScreen(observeLifeCycleAction, typeLifeCycleList)
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
                    .fillMaxWidth()
                    .padding(bottom = size5dp)
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
                            height = Dimension.fillToConstraints
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


@Composable
fun ContainerWithoutScroll(
    modifier: Modifier = Modifier,
    bodyColor: Color = LocalCustomColors.current.colorPrimaryBg,
    header: @Composable () -> Unit = {},
    body: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    observeLifeCycleAction: () -> Unit = {},
    typeLifeCycleList: ArrayList<TypeLifeCycle> = arrayListOf(),
) {
    SetObserversScreen(observeLifeCycleAction, typeLifeCycleList)
    if (LoadingManager.loaderState().value) {
        PulseLoader()
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = bodyColor)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .imePadding()
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
                        .background(LocalCustomColors.current.colorPrimaryBg)
                ) {
                    footer()
                }
            }
        }
    }
}

@Composable
fun SetObserversScreen(observeLifeCycleAction: () -> Unit, typeLifeCycleList: ArrayList<TypeLifeCycle>) {
    val coroutineScope = rememberCoroutineScope()

    ObservableScreen(
        onResume = {
            if (typeLifeCycleList.contains(TypeLifeCycle.ON_RESUME)) {
                coroutineScope.launch {
                    observeLifeCycleAction()
                }
            }
        },
        onStarted = {
            if (existsLifeCycleEvent(typeLifeCycleList, TypeLifeCycle.ON_START)) {
                coroutineScope.launch {
                    observeLifeCycleAction()
                }
            }
        },
        onCreated = {
            if (existsLifeCycleEvent(typeLifeCycleList, TypeLifeCycle.ON_CREATE)) {
                coroutineScope.launch {
                    observeLifeCycleAction()
                }
            }
        },
        onDestroyed = {
            if (existsLifeCycleEvent(typeLifeCycleList, TypeLifeCycle.ON_DESTROY)) {
                coroutineScope.launch {
                    observeLifeCycleAction()
                }
            }
        },
        onInitialized = {
            if (existsLifeCycleEvent(typeLifeCycleList, TypeLifeCycle.ON_INITIALIZED)) {
                coroutineScope.launch {
                    observeLifeCycleAction()
                }
            }
        }
    )


}

fun existsLifeCycleEvent(list: ArrayList<TypeLifeCycle>, typeLifeCycle: TypeLifeCycle): Boolean {
    return list.contains(typeLifeCycle)
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

@Composable
fun FlatContainer(
    modifier: Modifier = Modifier,
    bodyColor: Color = LocalCustomColors.current.colorPrimaryBg,
    content: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = bodyColor)
    ) {
        content()
    }
}

@Preview
@Composable
fun ContainerContainerWithScrollPreview() {
    ContainerWithScroll(
        header = {
            Column(modifier = Modifier.size(size50dp).fillMaxWidth()) {
                CustomTextMediumBold("Prueba header", modifier = Modifier.fillMaxWidth())
            }
        },
        body = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                    }
                }
            }
        },
        footer = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(size10dp)
            ) {
                CustomButton(buttonText = "Bton prueba", onClick = {})
            }
        }

    )
}

@Preview
@Composable
fun ContainerContainerWithoutScrollPreview() {
    ContainerWithoutScroll(
        header = {
            Column(modifier = Modifier.size(size50dp).fillMaxWidth()) {
                CustomTextMediumBold("Prueba header", modifier = Modifier.fillMaxWidth())
            }
        },
        body = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                        CustomCard(
                            imageId = R.drawable.parking_complex_logo,
                            cardModifier = Modifier.size(size100dp)
                        )
                    }
                }
            }
        },
        footer = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(size10dp)
            ) {
                CustomButton(buttonText = "Bton prueba", onClick = {})
            }
        }

    )
}