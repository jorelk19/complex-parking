package com.complexparking.ui.edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.ContainerWithScroll
import com.complexparking.ui.base.CustomHeader

@Composable
fun EditScreen(
    navController: NavController
) {
    EditScreenContainer()
}

@Composable
private fun EditScreenContainer(

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
