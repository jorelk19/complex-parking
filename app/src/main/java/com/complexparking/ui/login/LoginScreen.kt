package com.complexparking.ui.login

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.Dimensions.size100dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size50dp
import com.complexparking.ui.base.EnumEditTextType
import com.complexparking.ui.base.MainContainer
import com.complexparking.ui.main.MainActivity
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.validateError
import org.koin.java.KoinJavaComponent.inject

@Composable
fun LoginScreen(
    navController: NavController,
) {
    val loginScreenViewModel: LoginScreenViewModel by inject(LoginScreenViewModel::class.java)
    if (loginScreenViewModel.goToHome.value) {
        loginScreenViewModel.goToHome.value = false
        val context = LocalContext.current
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
    MainContainer(
        body = {
            val userText = remember { mutableStateOf("") }
            val passText = remember { mutableStateOf("") }
            val colors = LocalCustomColors.current
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = size50dp, end = size50dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.parking_complex_logo),
                    contentDescription = null,
                    modifier = Modifier.size(size100dp)
                )
                Spacer(
                    modifier = Modifier.height(size30dp)
                )
                CustomEditText(
                    text = userText.value,
                    titleText = stringResource(id = R.string.login_screen_user_title),
                    onValueChange = { text ->
                        loginScreenViewModel.loginScreenModel.value.let { textChange ->
                            userText.value = text
                            textChange.onTextEmailChange(text)
                        }
                    },
                    imageStart = ImageVector.vectorResource(R.drawable.ic_email),
                    hasFocus = true,
                    bottomText = validateError(
                        hasError = loginScreenViewModel.loginScreenModel.value.emailError,
                        errorType = loginScreenViewModel.loginScreenModel.value.emailErrorType
                    ),
                    hasError = loginScreenViewModel.loginScreenModel.value.emailError,
                    typeText = EnumEditTextType.EMAIL
                )
                Spacer(
                    modifier = Modifier.height(size30dp)
                )
                CustomEditText(
                    text = passText.value,
                    titleText = stringResource(id = R.string.login_screen_password_title),
                    bottomText = "",
                    onValueChange = { text ->
                        loginScreenViewModel.loginScreenModel.let { textChange ->
                            passText.value = text
                            textChange.value.onTextPasswordChange(text)
                        }
                    },
                    imageStart = ImageVector.vectorResource(R.drawable.ic_padlock),
                    hasFocus = false,
                    hasError = loginScreenViewModel.loginScreenModel.value.passwordError,
                    typeText = EnumEditTextType.PASSWORD
                )
                Spacer(
                    modifier = Modifier.height(size30dp)
                )
                CustomButton(
                    onClick = {
                        loginScreenViewModel.goToHome.value = true
                    },
                    buttonText = stringResource(id = R.string.login_screen_button_access),
                    isEnabled = loginScreenViewModel.loginScreenModel.value.isButtonAccessEnabled
                )
            }
        }
    )
}
