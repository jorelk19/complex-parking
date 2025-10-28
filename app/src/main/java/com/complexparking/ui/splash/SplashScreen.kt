package com.complexparking.ui.splash

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithoutScroll
import com.complexparking.ui.base.Dimensions.size100dp
import com.complexparking.ui.base.Dimensions.size180dp
import com.complexparking.ui.main.MainActivity
import com.complexparking.ui.navigation.AppScreens
import com.complexparking.ui.wizard.WizardActivity
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(navController: NavController) {
    val splashViewModel: SplashScreenViewModel = koinViewModel()
    val isSplashCompleted by splashViewModel.isCompletedLoadingData.collectAsStateWithLifecycle()
    val goToHome by splashViewModel.goToHome
    val isWizardCompleted by splashViewModel.isWizardCompleted
    ScreenObserver(splashViewModel)
    ContainerWithoutScroll {
        SplashBody(
            navController,
            goToHome,
            isWizardCompleted,
            isSplashCompleted
        )
    }
}

@Composable
private fun SplashFooter() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { }) {
            Text(
                text = "Click aqui"
            )
        }
    }
}

@Composable
private fun SplashBody(
    navController: NavController,
    goToHome: Boolean,
    isWizardCompleted: Boolean,
    isSplashCompleted: Boolean,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size(size180dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(size100dp)
        ) {
            ConstraintLayout(
                modifier = Modifier.wrapContentSize()
            ) {
                val image = createRef()
                Image(
                    painter = painterResource(id = R.drawable.parking_complex_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size180dp)
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }
        val context = LocalContext.current
        val activity = context as? Activity

        if (isSplashCompleted) {
            if (isWizardCompleted) {
                if (goToHome) {
                    val activity = context as? Activity
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    activity?.finish()
                } else {
                    navController.navigate(route = AppScreens.LOGINSCREEN.route)
                }
            } else {
                val intent = Intent(context, WizardActivity::class.java)
                context.startActivity(intent)
                activity?.finish()
            }
        }
    }
}

@Composable
fun ScreenObserver(splashViewModel: SplashScreenViewModel) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                splashViewModel.validateShowWizard()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen(navController = rememberNavController())
}