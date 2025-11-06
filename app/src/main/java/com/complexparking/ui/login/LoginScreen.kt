package com.complexparking.ui.login

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithScroll
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.CustomText3XLageBold
import com.complexparking.ui.base.Dimensions.size100dp
import com.complexparking.ui.base.Dimensions.size10dp
import com.complexparking.ui.base.Dimensions.size15dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size50dp
import com.complexparking.ui.base.EnumEditTextType
import com.complexparking.ui.main.MainActivity
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.validateError
import com.complexparking.ui.widgets.CustomCard
import com.complexparking.ui.widgets.CustomSurface
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
) {
    val loginScreenViewModel: LoginScreenViewModel = koinViewModel()
    val isCompletedLoading by loginScreenViewModel.isCompletedLoadingData.collectAsStateWithLifecycle()
    val model = loginScreenViewModel.loginScreenModel.value
    if (loginScreenViewModel.goToHome.value) {
        loginScreenViewModel.goToHome.value = false
        val context = LocalContext.current
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
    LoginScreenBody(
        model = model,
        onClickAccess = { model.onClickAccess() },
        onPasswordChange = { loginScreenViewModel.onPasswordChange(it) },
        onEmailChange = { loginScreenViewModel.onEmailChange(it) }
    )
}

@Composable
fun LoginScreenBody(
    model: LoginScreenModel,
    onClickAccess: () -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
) {
    val colors = LocalCustomColors.current
    val userText = remember { mutableStateOf("") }
    val passText = remember { mutableStateOf("") }
    ContainerWithScroll(
        header = {
            Box(
                modifier = Modifier.padding(size30dp),
                contentAlignment = Alignment.CenterStart
            ) {
                CustomText3XLageBold(
                    text = stringResource(R.string.login_screen_welcom_greeting)
                )
            }
        },
        body = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = size50dp, end = size50dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(size15dp))
                    CustomCard(
                        imageId = R.drawable.parking_complex_logo,
                        cardModifier = Modifier.size(size100dp)
                    )
                    Spacer(
                        modifier = Modifier.height(size30dp)
                    )
                    CustomSurface {
                        CustomEditText(
                            text = userText.value,
                            titleText = stringResource(id = R.string.login_screen_user_title),
                            onValueChange = { text ->
                                model.let { textChange ->
                                    userText.value = text
                                    onEmailChange(text)
                                }
                            },
                            imageStart = ImageVector.vectorResource(R.drawable.ic_email),
                            hasFocus = true,
                            bottomText = validateError(
                                hasError = model.emailError,
                                errorType = model.emailErrorType
                            ),
                            hasError = model.emailError,
                            typeText = EnumEditTextType.EMAIL
                        )
                        Spacer(
                            modifier = Modifier.height(size10dp)
                        )
                        CustomEditText(
                            text = passText.value,
                            titleText = stringResource(id = R.string.login_screen_password_title),
                            bottomText = "",
                            onValueChange = { text ->
                                model.let { textChange ->
                                    passText.value = text
                                    onPasswordChange(text)
                                }
                            },
                            imageStart = ImageVector.vectorResource(R.drawable.ic_padlock),
                            hasFocus = false,
                            hasError = model.passwordError,
                            typeText = EnumEditTextType.PASSWORD
                        )
                        Spacer(
                            modifier = Modifier.height(size15dp)
                        )
                        CustomButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onClickAccess()
                            },
                            buttonText = stringResource(id = R.string.login_screen_button_access),
                            isEnabled = model.isButtonAccessEnabled
                        )
                    }
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun LoginScreenBodyPreview() {
    LoginScreenBody(
        LoginScreenModel()
    )
}
