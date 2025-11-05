package com.complexparking.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.complexparking.R
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.CustomTextMediumBold
import com.complexparking.ui.base.Dimensions.size10dp
import com.complexparking.ui.base.Dimensions.size20dp
import com.complexparking.ui.base.Dimensions.size2dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size40dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.controls.CustomImage
import com.complexparking.ui.splash.SplashScreenState
import com.complexparking.ui.theme.LocalCustomColors
import org.koin.androidx.compose.koinViewModel

@Composable
fun CustomGeneralHeader(
) {
    val viewModel: HeaderViewModel = koinViewModel()
    viewModel.isCompletedLoadingData.collectAsStateWithLifecycle()
    val uiState by viewModel.headerState.collectAsStateWithLifecycle()
    CustomGeneralHeaderContainer(
        uiState = uiState
    )
}

@Composable
private fun CustomGeneralHeaderContainer(
    uiState: HeaderState,
) {
    val colors = LocalCustomColors.current
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = size20dp, end = size20dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomImage(
                imageResourceId = R.drawable.ic_profile,
                modifier = Modifier.size(size30dp),

                )
            Spacer(modifier = Modifier.width(size10dp))
            Column {
                CustomTextMediumBold(stringResource(R.string.home_screen_header_profile_greetings, uiState.userData.name))
                Spacer(modifier = Modifier.width(size5dp))
                CustomTextMedium(
                    text = if (uiState.userData.isAdmin) {
                        stringResource(R.string.home_screen_header_profile_admin)
                    } else {
                        stringResource(R.string.home_screen_header_profile_security_guard)
                    }
                )
            }
        }
        HorizontalDivider(modifier = Modifier.height(size2dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CustomGeneralHeaderPreview() {
    CustomGeneralHeaderContainer(
        uiState = HeaderState()
    )
}