package com.complexparking.ui.user

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithScroll
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.Dimensions.size50dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.EnumEditTextType
import com.complexparking.ui.navigation.AppScreens
import com.complexparking.ui.validateError
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateUserScreen(navController: NavController) {
    val viewModel: CreateUserScreenViewModel = koinViewModel()
    val uiState by viewModel.createUserState.collectAsState()

    BackHandler(enabled = true) {
        navController.navigate(AppScreens.SETTINGSCREEN.route)
    }

    ContainerWithScroll(
        header = {
            CustomHeader(
                headerTitle = stringResource(R.string.create_user_screen_title),
                modifier = Modifier.fillMaxSize(),
                imageStart = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                onClickStart = { navController.navigate(AppScreens.SETTINGSCREEN.route) }
            )
        },
        body = {
            CreateUserBody(
                uiState = uiState,
                onNameChange = { viewModel.onNameChange(it) },
                onEmailChange = { viewModel.onEmailChange(it) },
                onPasswordChange = { viewModel.onPasswordChange(it) },
                onRepeatPasswordChange = { viewModel.onRepeatPasswordChange(it) },
                onClickCreate = { viewModel.onCreateUserClick() },
                onCheckedAdmin = { viewModel.onCheckedAdmin(it) }
            )
        }
    )
}

@Composable
private fun CreateUserBody(
    uiState: CreateUserState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onClickCreate: () -> Unit,
    onCheckedAdmin: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = size50dp, end = size50dp)
    ) {
        CustomEditText(
            text = uiState.name,
            titleText = stringResource(id = R.string.create_user_screen_user_name),
            onValueChange = { text ->
                onNameChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_house),
            hasFocus = true,
            bottomText = "",
            typeText = EnumEditTextType.DEFAULT
        )
        Spacer(
            modifier = Modifier.height(size5dp)
        )
        CustomEditText(
            text = uiState.email,
            titleText = stringResource(id = R.string.wizard_user_creation_user),
            onValueChange = { text ->
                onEmailChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_email),
            hasFocus = false,
            bottomText = validateError(
                hasError = uiState.userEmailError,
                errorType = uiState.userEmailErrorType
            ),
            hasError = uiState.userEmailError,
            typeText = EnumEditTextType.EMAIL
        )
        Spacer(
            modifier = Modifier.height(size5dp)
        )
        CustomEditText(
            text = uiState.password,
            titleText = stringResource(id = R.string.wizard_user_creation_user_password),
            bottomText = validateError(
                hasError = uiState.errorPassword,
                errorType = uiState.passwordErrorType
            ),
            onValueChange = { text ->
                //userPassText.value = text
                onPasswordChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_padlock),
            hasFocus = false,
            hasError = uiState.errorPassword,
            typeText = EnumEditTextType.PASSWORD
        )
        Spacer(
            modifier = Modifier.height(size5dp)
        )
        CustomEditText(
            text = uiState.repeatPassword,
            titleText = stringResource(id = R.string.wizard_user_creation_user_repeat_password),
            bottomText = validateError(
                hasError = uiState.errorRepeatPassword,
                errorType = uiState.repeatPasswordErrorType
            ),
            onValueChange = { text ->
                //repeatUserPassText.value = text
                onRepeatPasswordChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_padlock),
            hasFocus = false,
            hasError = uiState.errorRepeatPassword,
            typeText = EnumEditTextType.PASSWORD
        )
        Spacer(
            modifier = Modifier.height(size5dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                checked = uiState.isAdmin,
                onCheckedChange = {
                    onCheckedAdmin(it)
                }
            )
            CustomTextMedium(
                text = stringResource(R.string.create_user_screen_is_admin)
            )
        }
        // --- End of new code ---
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onClickCreate()
                },
                buttonText = stringResource(id = R.string.create_user_screen_user_button_title),
                isEnabled = uiState.isButtonCreateEnabled
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateUsersScreenPreview() {
    CreateUserBody(
        uiState = CreateUserState(),
        {},
        {},
        {},
        {},
        {},
        {}
    )
}