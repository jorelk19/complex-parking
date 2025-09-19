package com.complexparking.ui.wizard

import android.app.Activity
import android.content.Intent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.complexparking.R
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.Dimensions.size150dp
import com.complexparking.ui.base.Dimensions.size80dp
import com.complexparking.ui.base.Dimensions.spacingMedium
import com.complexparking.ui.base.FlatContainer
import com.complexparking.ui.base.SimpleContainer
import com.complexparking.ui.splash.SplashActivity
import com.complexparking.ui.theme.LocalCustomColors
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

@Composable
fun WizardScreen(navController: NavController) {
    val colors = LocalCustomColors.current
    FlatContainer {
        WizardScreenBody(
            navController
        )
    }
}

@Composable
private fun WizardScreenBody(navController: NavController) {
    val wizardScreenViewModel: WizardScreenViewModel by inject(WizardScreenViewModel::class.java)
    val wizardModel = wizardScreenViewModel.wizardModel

    if (wizardScreenViewModel.gotoLoginScreen.value) {
        val context = LocalContext.current
        val activity = context as? Activity
        val intent = Intent(context, SplashActivity::class.java)
        context.startActivity(intent)
        wizardScreenViewModel.gotoLoginScreen.value = false
        activity?.finish()
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 3 }
    )

    var userScrollEnabled by remember { mutableStateOf(false) }

    SimpleContainer(
        body = {
            HorizontalPager(
                state = pagerState,
                pageSpacing = spacingMedium,
                pageSize = PageSize.Fill,
                userScrollEnabled = userScrollEnabled,
                modifier = Modifier.wrapContentSize()
            ) { index ->
                when (index) {
                    0 -> {
                        WizardComplexConfigurationScreen(navController)
                    }

                    1 -> {
                        UploadComplexDataScreen(navController)
                    }

                    2 -> {
                        WizardUserCreationScreen(navController)
                    }
                }
            }
        },
        footer = {
            WizardFooter(wizardScreenViewModel, wizardModel, pagerState)
        }
    )
}

@Composable
private fun WizardFooter(wizardScreenViewModel: WizardScreenViewModel, wizardModel: MutableState<WizardScreenModel>, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    Row(modifier = Modifier.padding(start = spacingMedium, end = spacingMedium).fillMaxWidth()) {
        if (wizardScreenViewModel.isButtonPreviousVisible.value) {
            CustomButton(
                modifier = Modifier
                    .wrapContentSize()
                    .width(size150dp),
                onClick = {
                    wizardModel.value.onClickPreviousStep(wizardScreenViewModel.currentStep.value)
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(wizardScreenViewModel.currentIndex.value)
                    }
                },
                buttonText = stringResource(id = R.string.wizard_complex_configuration_previous_button),
                isEnabled = wizardModel.value.isButtonEnabled
            )
        }
        CustomButton(
            modifier = Modifier
                .wrapContentSize()
                .width(size150dp),
            onClick = {
                wizardModel.value.onClickNextStep(wizardScreenViewModel.currentStep.value)
                coroutineScope.launch {
                    pagerState.animateScrollToPage(wizardScreenViewModel.currentIndex.value)
                }
            },
            buttonText = stringResource(id = wizardModel.value.buttonText),
            isEnabled = wizardModel.value.isButtonEnabled
        )
    }
}

fun navigateTo(currentStep: MutableState<EnumWizardStep>): Int {
    return when (currentStep.value) {
        EnumWizardStep.STEP1 -> 0
        EnumWizardStep.STEP2 -> 1
        EnumWizardStep.STEP3 -> 2
    }
}

@Preview
@Composable
fun PreviewWizardScreen() {
    WizardScreen(rememberNavController())
}