package com.complexparking.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.complexparking.R
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.Dimensions.size10dp
import com.complexparking.ui.base.Dimensions.size2dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size40dp
import com.complexparking.ui.controls.CustomImage
import com.complexparking.ui.theme.LocalCustomColors

@Composable
fun CustomGeneralHeader(
    headerText: String
){
    val colors = LocalCustomColors.current
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(size40dp).padding(start = size10dp, end = size10dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomImage(
                imageResourceId = R.drawable.ic_profile,
                modifier = Modifier.size(size30dp),

            )
            Spacer(modifier = Modifier.width(size10dp))
            CustomTextMedium(headerText)
        }
        HorizontalDivider(modifier = Modifier.height(size2dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CustomGeneralHeaderPreview() {
    CustomGeneralHeader("¡Hola! Edson Nieto")
}