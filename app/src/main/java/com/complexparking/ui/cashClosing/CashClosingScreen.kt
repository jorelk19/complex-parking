package com.complexparking.ui.cashClosing

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithScroll
import com.complexparking.ui.base.CustomHeader

@Composable
fun CashClosingScreen(
    navController: NavController
) {
    CashClosingScreenContainer()
}

@Composable
private fun CashClosingScreenContainer(

){
    ContainerWithScroll(
        header =  {
            CustomHeader(headerTitle = stringResource(R.string.edit_screen_title))
        },
        body = {
            EditScreenBody()
        }
    )
}

@Composable
private fun EditScreenBody() {

}
