package com.complexparking.ui.base

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.complexparking.ui.theme.LocalCustomColors

@Composable
fun CustomTextSmall(
    text: String,
    color: Color = LocalCustomColors.current.colorPrimaryText,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        modifier = modifier
            .wrapContentSize(),
        fontSize = Dimensions.text12sp,
        textAlign = textAlign
    )
}

@Composable
fun CustomTextMedium(
    text: String,
    color: Color = LocalCustomColors.current.colorPrimaryText,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        modifier = modifier
            .fillMaxWidth(),
        fontSize = Dimensions.text14sp,
        textAlign = textAlign
    )
}

@Composable
fun CustomTextLage(
    text: String,
    color: Color = LocalCustomColors.current.colorPrimaryText,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        modifier = modifier
            .fillMaxWidth(),
        fontSize = Dimensions.text16sp,
        textAlign = textAlign
    )
}

@Composable
fun CustomTextXLage(
    text: String,
    color: Color = LocalCustomColors.current.colorPrimaryText,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        modifier = modifier
            .fillMaxWidth(),
        fontSize = Dimensions.text18sp,
        textAlign = textAlign
    )
}

@Composable
fun CustomText2XLage(
    text: String,
    color: Color = LocalCustomColors.current.colorPrimaryText,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        modifier = modifier
            .fillMaxWidth(),
        fontSize = Dimensions.text20sp,
        textAlign = textAlign
    )
}

@Composable
fun CustomText3XLage(
    text: String,
    color: Color = LocalCustomColors.current.colorPrimaryText,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        modifier = modifier
            .fillMaxWidth(),
        fontSize = Dimensions.text24sp,
        textAlign = textAlign
    )
}

@Composable
fun CustomText4XLage(
    text: String,
    color: Color = LocalCustomColors.current.colorPrimaryText,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        modifier = modifier
            .fillMaxWidth(),
        fontSize = Dimensions.text28sp,
        textAlign = textAlign
    )
}

@Composable
fun CustomText5XLage(
    text: String,
    color: Color = LocalCustomColors.current.colorPrimaryText,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        modifier = modifier
            .fillMaxWidth(),
        fontSize = Dimensions.text32sp,
        textAlign = textAlign
    )
}

@Composable
fun CustomText6XLage(
    text: String,
    color: Color = LocalCustomColors.current.colorPrimaryText,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        modifier = modifier
            .fillMaxWidth(),
        fontSize = Dimensions.text42sp,
        textAlign = textAlign
    )
}