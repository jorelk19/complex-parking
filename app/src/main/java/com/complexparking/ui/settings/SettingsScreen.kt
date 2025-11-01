package com.complexparking.ui.settings

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithScroll
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.CustomTextLage
import com.complexparking.ui.base.CustomTextLageBold
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.Dimensions.size10dp
import com.complexparking.ui.base.Dimensions.size20dp
import com.complexparking.ui.base.Dimensions.size50dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.Dimensions.size80dp
import com.complexparking.ui.navigation.AppScreens
import com.complexparking.ui.splash.SplashActivity
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.widgets.CustomCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
) {
    val viewModel: SettingsScreenViewModel = koinViewModel()
    viewModel.isCompletedLoadingData.collectAsState()
    val uiState by viewModel.settingScreenState.collectAsState()

    if (uiState.screenTarget != AppScreens.NONE) {
        if (uiState.screenTarget == AppScreens.LOGINSCREEN) {
            val context = LocalContext.current
            val intent = Intent(context, SplashActivity::class.java)
            context.startActivity(intent)
        } else {
            navController.navigate(uiState.screenTarget.route)
        }
    }

    ContainerWithScroll(
        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.settings_screen_title),
                modifier = Modifier.fillMaxSize()
            )
        },
        body = {
            SettingsBody(
                uiState = uiState,
                onItemSelectionAction = { viewModel.onItemSelected(it) }
            )
        }
    )
}

@Composable
fun SettingsBody(
    uiState: SettingScreenState,
    onItemSelectionAction: (SettingsMenuItem) -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(start = size20dp, end = size20dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = size20dp, bottom = size20dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomCard(
                cardModifier = Modifier.size(size80dp),
                imageId = R.drawable.ic_settings_gear,
                imagePadding = size5dp
            )
        }
        Spacer(
            modifier = Modifier
                .height(size5dp)
                .padding(top = size5dp)
        )
        CustomTextLageBold(
            text = stringResource(R.string.settings_screen_profile_section_title)
        )
        Spacer(
            modifier = Modifier
                .height(size5dp)
                .padding(top = size5dp)
        )
        PromptUserInfo(uiState)
        Spacer(
            modifier = Modifier
                .height(size5dp)
                .padding(top = size5dp)
        )
        CustomTextLageBold(
            text = stringResource(R.string.settings_screen_menu_section_title)
        )
        Spacer(
            modifier = Modifier
                .height(size5dp)
                .padding(top = size5dp)
        )
        if (uiState.userData.isAdmin) {
            CustomMenuItem(
                text = stringResource(R.string.settings_screen_menu_parking_parameters),
                onClickItem = { onItemSelectionAction(SettingsMenuItem.PARAMETERS_PARKING_ITEM) }
            )
            HorizontalDivider(Modifier.height(size5dp))
            CustomMenuItem(
                text = stringResource(R.string.settings_screen_menu_parking_create_user),
                onClickItem = { onItemSelectionAction(SettingsMenuItem.CREATE_USER_ITEM) }
            )
            HorizontalDivider(Modifier.height(size5dp))
        }
        CustomMenuItem(
            text = stringResource(R.string.settings_screen_menu_printer),
            onClickItem = { onItemSelectionAction(SettingsMenuItem.PRINTER_ITEM) }
        )
        HorizontalDivider(Modifier.height(size5dp))
        CustomMenuItem(
            text = stringResource(R.string.settings_screen_menu_parking_close_session),
            onClickItem = { onItemSelectionAction(SettingsMenuItem.CLOSE_SESSION_ITEM) }
        )
        HorizontalDivider(Modifier.height(size5dp))
    }
}

@Composable
fun PromptUserInfo(uiState: SettingScreenState) {
    val colors = LocalCustomColors.current
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(size10dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = size5dp
        ),
        colors = CardDefaults.cardColors(colors.colorPrimaryBgCard)
    ) {
        Column(
            modifier = Modifier.padding(size10dp)
        ) {
            CustomTextLage(
                text = stringResource(R.string.settings_screen_profile_banner, uiState.userData.name)
            )
            Spacer(modifier = Modifier.height(size10dp))
            CustomTextLage(
                text = stringResource(R.string.settings_screen_profile_email, uiState.userData.email)
            )
        }
    }
}

@Composable
fun CustomMenuItem(text: String, onClickItem: () -> Unit = {}) {
    val colors = LocalCustomColors.current
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = size5dp
        ),
        modifier = Modifier
            .height(size50dp)
            .fillMaxWidth()
            .padding(size10dp)
            .clickable {
                onClickItem()
            },
        shape = RoundedCornerShape(size5dp),
        colors = CardDefaults.cardColors(colors.colorPrimaryBgCard)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val containerText = createRef()
            CustomTextMedium(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = size20dp, end = size20dp)
                    .constrainAs(containerText) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomMenuItemPreview() {
    CustomMenuItem("Menu item text")
}


@Preview(showBackground = true)
@Composable
fun SettingsBodyPreview() {
    SettingsBody(
        SettingScreenState()
    )
}