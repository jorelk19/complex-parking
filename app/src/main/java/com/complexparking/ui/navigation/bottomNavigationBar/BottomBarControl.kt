package com.complexparking.ui.navigation.bottomNavigationBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.complexparking.R
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.Dimensions
import com.complexparking.ui.navigation.AppScreens
import com.complexparking.ui.theme.LocalCustomColors

@Composable
fun BottomBarControl(navController: NavController) {
    val colors = LocalCustomColors.current
    val selectedColor = colors.colorPrimaryBgDefault
    val unselectedColor = colors.colorNeutralBgDefault
    val colorTransparent = Color.Transparent

    val homeTab = BottomBarItem(
        title = stringResource(R.string.bottom_navigation_view_home),
        menuIcon = ImageVector.vectorResource(R.drawable.ic_home),
        selectedColor = selectedColor,
        unselectedColor = unselectedColor,
        targetScreen = AppScreens.HOMESCREEN,
        selectedIndicatorMenuIcon = ImageVector.vectorResource(R.drawable.selected_menu_rectangle)
    )
    val searchTab = BottomBarItem(
        title = stringResource(R.string.bottom_navigation_view_search),
        menuIcon = ImageVector.vectorResource(R.drawable.ic_feature_search),
        selectedColor = selectedColor,
        unselectedColor = unselectedColor,
        targetScreen = AppScreens.SEARCHSCREEN,
        selectedIndicatorMenuIcon = ImageVector.vectorResource(R.drawable.selected_menu_rectangle)
    )
    val editTab = BottomBarItem(
        title = stringResource(R.string.bottom_navigation_view_edit),
        menuIcon = ImageVector.vectorResource(R.drawable.ic_edit),
        selectedColor = selectedColor,
        unselectedColor = unselectedColor,
        targetScreen = AppScreens.EDITSCREEN,
        selectedIndicatorMenuIcon = ImageVector.vectorResource(R.drawable.selected_menu_rectangle)
    )
    val settingsTab = BottomBarItem(
        title = stringResource(R.string.bottom_navigation_view_menu),
        menuIcon = ImageVector.vectorResource(R.drawable.ic_settings),
        selectedColor = selectedColor,
        unselectedColor = unselectedColor,
        targetScreen = AppScreens.SETTINGSCREEN,
        selectedIndicatorMenuIcon = ImageVector.vectorResource(R.drawable.selected_menu_rectangle)
    )

    val tabBarItems = listOf(homeTab, searchTab, editTab, settingsTab)
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    HorizontalDivider(thickness = Dimensions.bottomNavigationBarHorizontalDividerThickness, color = colors.colorNeutralBorder)
    NavigationBar(
        modifier = Modifier
            .height(Dimensions.bottomNavigationBarHeight),
        containerColor = colors.colorNeutralBg
    ) {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.targetScreen.route)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        menuIcon = tabBarItem.menuIcon,
                        selectedIndicatorMenuIcon = tabBarItem.selectedIndicatorMenuIcon,
                        selectedColor = tabBarItem.selectedColor,
                        unselectedColor = tabBarItem.unselectedColor,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount,
                        iconBackgroundColor = Color.Transparent
                    )
                },
                label = {
                    CustomTextMedium(
                        text = tabBarItem.title,
                        textAlign = TextAlign.Center,
                        color = if (selectedTabIndex == index) colors.colorPrimaryBgDefault else colors.colorPrimaryTextGray
                    )
                },
                modifier = Modifier.background(color = Color.Transparent).height(Dimensions.bottomNavigationBarItemHeight).padding(bottom = Dimensions.bottomNavigationBarBottom),
                colors = NavigationBarItemColors(
                    selectedIconColor = colorTransparent,
                    selectedIndicatorColor = colorTransparent,
                    unselectedIconColor = colorTransparent,
                    unselectedTextColor = colorTransparent,
                    disabledIconColor = colorTransparent,
                    disabledTextColor = colorTransparent,
                    selectedTextColor = colorTransparent
                )
            )
        }
    }
}

@Composable
private fun TabBarIconView(
    isSelected: Boolean,
    menuIcon: ImageVector,
    selectedIndicatorMenuIcon: ImageVector,
    selectedColor: Color,
    unselectedColor: Color,
    title: String,
    badgeAmount: Int? = null,
    iconBackgroundColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = selectedIndicatorMenuIcon,
                tint = selectedColor,
                contentDescription = "",
                modifier = Modifier.height(Dimensions.bottomNavigationBarItemIndicatorHeight)
            )
            Spacer(Modifier.height(Dimensions.bottomNavigationBarIconTopSpacerWithSelectorIndicator))
        } else {
            Spacer(Modifier.height(Dimensions.bottomNavigationBarIconTopSpacerWithoutSelectorIndicator))
        }
        BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
            Icon(
                imageVector = menuIcon,
                tint = if (isSelected) {
                    selectedColor
                } else {
                    unselectedColor
                },
                contentDescription = title,
                modifier = Modifier.background(color = iconBackgroundColor).width(Dimensions.bottomNavigationBarIconItemWidth).height(Dimensions.bottomNavigationBarIconItemHeight)
            )
        }
    }
}

@Composable
private fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}

@Preview
@Composable
fun TestBottomBar() {
    val navController = rememberNavController()
    BottomBarControl(navController = navController)
}

private data class BottomBarItem(
    val title: String,
    val menuIcon: ImageVector,
    val selectedColor: Color,
    val unselectedColor: Color,
    val targetScreen: AppScreens,
    val selectedIndicatorMenuIcon: ImageVector,
    val badgeAmount: Int? = null
)
