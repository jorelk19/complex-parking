package com.complexparking.ui.wizard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.complexparking.R
import com.complexparking.ui.base.CustomEditText
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.Dimensions.size2dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size40dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.EnumEditTextType
import com.complexparking.ui.base.ContainerWithScroll
import com.complexparking.ui.validateError
import org.koin.androidx.compose.koinViewModel

@Composable
fun WizardUserCreationScreen() {
    val wizardScreenViewModel: WizardScreenViewModel = koinViewModel()
    val model by wizardScreenViewModel.wizardModel.collectAsState()
    ContainerWithScroll(
        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.wizard_create_user_title),
                modifier = Modifier.fillMaxSize()
            )
        },
        body = {
            WizardCreateUserBody(
                model = model,
                onAdminEmailChange = { wizardScreenViewModel.onAdminEmailChange(it) },
                onAdminPasswordChange = { wizardScreenViewModel.onAdminPasswordChange(it) },
                onRepeatAdminPasswordChange = { wizardScreenViewModel.onRepeatAdminPasswordChange(it) },
                onUserEmailChange = { wizardScreenViewModel.onUserEmailChange(it) },
                onUserPasswordChange = { wizardScreenViewModel.onUserPasswordChange(it) },
                onRepeatUserPasswordChange = { wizardScreenViewModel.onRepeatUserPasswordChange(it) }
            )
        }
    )
}

@Composable
private fun WizardCreateUserBody(
    model: WizardScreenModel,
    onAdminEmailChange: (String) -> Unit = {},
    onAdminPasswordChange: (String) -> Unit = {},
    onRepeatAdminPasswordChange: (String) -> Unit = {},
    onUserEmailChange: (String) -> Unit = {},
    onUserPasswordChange: (String) -> Unit = {},
    onRepeatUserPasswordChange: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = size5dp, start = size30dp, end = size30dp, bottom = size40dp)
    ) {
        val adminUserText = remember { mutableStateOf("") }
        val adminPassText = remember { mutableStateOf("") }
        val repeatAdminPassText = remember { mutableStateOf("") }
        val adminName = remember { mutableStateOf("") }
        val userText = remember { mutableStateOf("") }
        val userPassText = remember { mutableStateOf("") }
        val repeatUserPassText = remember { mutableStateOf("") }
        HorizontalDivider(modifier = Modifier.height(size2dp))
        CustomEditText(
            text = adminUserText.value,
            titleText = stringResource(id = R.string.wizard_user_creation_admin_user),
            onValueChange = { text ->
                adminUserText.value = text
                onAdminEmailChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_email),
            hasFocus = true,
            bottomText = validateError(
                hasError = model.adminEmailError,
                errorType = model.adminEmailErrorType
            ),
            hasError = model.adminEmailError,
            typeText = EnumEditTextType.EMAIL
        )
        CustomEditText(
            text = adminPassText.value,
            titleText = stringResource(id = R.string.wizard_user_creation_admin_password),
            bottomText = validateError(
                hasError = model.errorAdminPassword,
                errorType = model.adminPasswordErrorType
            ),
            onValueChange = { text ->
                onAdminPasswordChange(text)
                adminPassText.value = text
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_padlock),
            hasFocus = false,
            hasError = model.errorAdminPassword,
            typeText = EnumEditTextType.PASSWORD
        )
        CustomEditText(
            text = repeatAdminPassText.value,
            titleText = stringResource(id = R.string.wizard_user_creation_admin_repeat_password),
            bottomText = validateError(
                hasError = model.errorRepeatAdminPassword,
                errorType = model.repeatAdminPasswordErrorType
            ),
            onValueChange = { text ->
                repeatAdminPassText.value = text
                onRepeatAdminPasswordChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_padlock),
            hasFocus = false,
            hasError = model.errorRepeatAdminPassword,
            typeText = EnumEditTextType.PASSWORD
        )
        /*CustomEditText(
            text = userText.value,
            titleText = stringResource(id = R.string.wizard_user_creation_user),
            onValueChange = { text ->
                userText.value = text
                onUserEmailChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_email),
            hasFocus = false,
            bottomText = validateError(
                hasError = model.userEmailError,
                errorType = model.userEmailErrorType
            ),
            hasError = model.userEmailError,
            typeText = EnumEditTextType.EMAIL
        )
        CustomEditText(
            text = userPassText.value,
            titleText = stringResource(id = R.string.wizard_user_creation_user_password),
            bottomText = validateError(
                hasError = model.errorUserPassword,
                errorType = model.userPasswordErrorType
            ),
            onValueChange = { text ->
                userPassText.value = text
                onUserPasswordChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_padlock),
            hasFocus = false,
            hasError = model.errorUserPassword,
            typeText = EnumEditTextType.PASSWORD
        )
        CustomEditText(
            text = repeatUserPassText.value,
            titleText = stringResource(id = R.string.wizard_user_creation_user_repeat_password),
            bottomText = validateError(
                hasError = model.errorRepeatUserPassword,
                errorType = model.repeatUserPasswordErrorType
            ),
            onValueChange = { text ->
                repeatUserPassText.value = text
                onRepeatUserPasswordChange(text)
            },
            imageStart = ImageVector.vectorResource(R.drawable.ic_padlock),
            hasFocus = false,
            hasError = model.errorRepeatUserPassword,
            typeText = EnumEditTextType.PASSWORD
        )*/
    }
}

@Composable
@Preview(showBackground = true)
fun WizardCreateUserBodyPreview() {
    WizardCreateUserBody(
        WizardScreenModel()
    )
}