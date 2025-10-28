package com.complexparking.ui.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithoutScroll
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.theme.LocalCustomColors
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier,
) {
    val searchScreenViewModel: SearchScreenViewModel = koinViewModel()
    val searchModel by searchScreenViewModel.searchScreenModel
    val colors = LocalCustomColors.current
    val context = LocalContext.current

    ContainerWithoutScroll(
        modifier = modifier,
        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.search_screen_guest_title),
                modifier = Modifier.fillMaxSize()
            )
        },
        body = {
            SearchBody(
                modifier = Modifier.fillMaxSize(),
                searchScreenModel = searchModel
            )
        }
    )
}

@Composable
fun SearchBody(modifier: Modifier, searchScreenModel: SearchScreenModel) {
}