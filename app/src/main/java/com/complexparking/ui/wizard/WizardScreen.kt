package com.complexparking.ui.wizard

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.Dimensions.size150dp
import com.complexparking.ui.base.Dimensions.spacingMedium
import com.complexparking.ui.base.FlatContainer
import com.complexparking.ui.base.SimpleContainer
import com.complexparking.ui.splash.SplashActivity
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.utilities.ObservableScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun WizardScreen(navController: NavController) {
    val viewModel: WizardScreenViewModel = koinViewModel()
    val isCompletedData by viewModel.isCompletedLoadingData.collectAsStateWithLifecycle()
    val index = viewModel.currentIndex.value
    val buttonEnabled by viewModel.isButtonEnabled.collectAsState()
    val colors = LocalCustomColors.current
    if (viewModel.gotoLoginScreen.value) {
        val context = LocalContext.current
        val activity = context as? Activity
        val intent = Intent(context, SplashActivity::class.java)
        context.startActivity(intent)
        activity?.finish()
    }
    FlatContainer {
        WizardScreenBody(
            buttonText = viewModel.buttonText.value,
            isButtonPreviousVisible = viewModel.isButtonPreviousVisible.value,
            currentIndex = index,
            isButtonEnabled = buttonEnabled,
            onClickNextStep = { viewModel.onNextIndex() },
            onClickPreviousStep = { viewModel.onPreviousIndex() }
        )
    }
}

@Composable
private fun WizardScreenBody(
    isButtonPreviousVisible: Boolean,
    isButtonEnabled: Boolean,
    currentIndex: Int,
    buttonText: Int,
    onClickNextStep: () -> Int,
    onClickPreviousStep: () -> Int,
) {

    val currentPage = remember { mutableStateOf(0) }

    val pagerState = rememberPagerState(
        initialPage = currentPage.value,
        pageCount = { 3 }
    )

    SimpleContainer(
        body = {
            HorizontalPager(
                state = pagerState,
                pageSpacing = spacingMedium,
                pageSize = PageSize.Fill,
                userScrollEnabled = false,
                modifier = Modifier.wrapContentSize()
            ) { index ->
                when (index) {
                    0 -> {
                        WizardComplexConfigurationScreen()
                    }

                    1 -> {
                        UploadComplexDataScreen()
                    }

                    2 -> {
                        WizardUserCreationScreen()
                    }
                }
            }
        },
        footer = {
            WizardFooter(
                isButtonPreviousVisible = isButtonPreviousVisible,
                currentIndex = currentIndex,
                pagerState = pagerState,
                buttonText = buttonText,
                isButtonEnabled = isButtonEnabled,
                onClickNextStep = onClickNextStep,
                onClickPreviousStep = onClickPreviousStep
            )
        }
    )
}

@Composable
private fun WizardFooter(
    isButtonPreviousVisible: Boolean,
    buttonText: Int,
    isButtonEnabled: Boolean,
    pagerState: PagerState,
    onClickNextStep: () -> Int,
    onClickPreviousStep: () -> Int,
    currentIndex: Int,
) {
    val coroutineScope = rememberCoroutineScope()
    val index = remember { mutableStateOf(0) }

    ObservableScreen(
        onResume = {
            index.value = currentIndex
            coroutineScope.launch {
                pagerState.scrollToPage(index.value)
            }
        }
    )
    Row(
        modifier = Modifier
            .padding(start = spacingMedium, end = spacingMedium)
            .fillMaxWidth()
            .background(LocalCustomColors.current.colorPrimaryBg)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (previousButton, nextButton) = createRefs()
            if (isButtonPreviousVisible) {
                CustomButton(
                    modifier = Modifier
                        .wrapContentSize()
                        .width(size150dp)
                        .constrainAs(previousButton) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        },
                    onClick = {
                        coroutineScope.launch {
                            index.value = onClickPreviousStep()
                            pagerState.scrollToPage(index.value)
                        }
                    },
                    buttonText = stringResource(id = R.string.wizard_complex_configuration_previous_button)
                )
            }
            CustomButton(
                modifier = Modifier
                    .wrapContentSize()
                    .width(size150dp)
                    .constrainAs(nextButton) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },
                onClick = {
                    coroutineScope.launch {
                        index.value = onClickNextStep()
                        pagerState.scrollToPage(index.value)
                    }
                },
                buttonText = stringResource(id = buttonText),
                isEnabled = isButtonEnabled
            )
        }
    }
}

fun getNextIndex(currentPage: Int): Int {
    var index = currentPage
    if (index >= 0 && index < 2) {
        index = index + 1
    }
    return index
}

fun getPreviousIndex(currentPage: Int): Int {
    var index = currentPage
    if (index >= 0 && index < 2) {
        index = index - 1
    }
    return index
}

fun navigateTo(currentStep: MutableState<EnumWizardStep>): Int {
    return when (currentStep.value) {
        EnumWizardStep.STEP1 -> 0
        EnumWizardStep.STEP2 -> 1
        EnumWizardStep.STEP3 -> 2
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWizardScreen() {
    WizardScreenBody(
        isButtonPreviousVisible = true,
        currentIndex = 0,
        isButtonEnabled = true,
        buttonText = R.string.wizard_complex_configuration_next_button,
        onClickNextStep = { 0 },
        onClickPreviousStep = { 0 }
    )
}