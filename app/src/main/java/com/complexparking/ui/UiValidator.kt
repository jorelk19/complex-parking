package com.complexparking.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.complexparking.R
import com.complexparking.ui.utilities.ErrorType

@Composable
fun validateError(hasError: Boolean, errorType: ErrorType): String {
    if (hasError) {
        return when (errorType) {
            ErrorType.WRONG_USER_PASSWORD -> stringResource(R.string.login_screen_wrong_email_password)
            ErrorType.INVALID_EMAIL -> stringResource(R.string.login_screen_email_error)
            ErrorType.NONE -> stringResource(R.string.login_screen_no_error)
            ErrorType.INVALID_PASSWORD -> stringResource(R.string.invalid_password)
            ErrorType.REPEAT_PASSWORD_INVALID -> stringResource(R.string.repeat_password_invalid)
            ErrorType.EMPTY_UNIT -> stringResource(R.string.home_screen_unit_empty_error)
            ErrorType.INVALID_UNIT -> stringResource(R.string.home_screen_invalid_unit_error)
            ErrorType.EMPTY_PLATE -> stringResource(R.string.home_screen_plate_empty_error)
            ErrorType.INVALID_PLATE -> stringResource(R.string.home_screen_invalid_plate_error)
            ErrorType.USER_ADMIN_SAME_EMAIL -> stringResource(R.string.same_user_admin_email)
        }
    } else {
        return stringResource(R.string.login_screen_no_error)
    }
}
