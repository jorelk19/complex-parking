package com.complexparking.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.complexparking.R
import com.complexparking.ui.base.Dimensions.borderStrokeMedium
import com.complexparking.ui.base.Dimensions.cornerRadius
import com.complexparking.ui.base.Dimensions.size15dp
import com.complexparking.ui.base.Dimensions.size24dp
import com.complexparking.ui.base.Dimensions.size48dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.theme.LocalCustomColors


enum class EnumEditTextType(val value: String) {
    PASSWORD("PASSWORD"),
    EMAIL("EMAIL"),
    DEFAULT("DEFAULT"),
    NUMBER("NUMBER"),
    LONG_NUMBER("LONG_NUMBER");

    companion object {
        fun getName(value: String): EnumEditTextType? {
            return EnumEditTextType.entries.firstOrNull { it.value == value }
        }
    }
}

@Composable
fun CustomEditText(
    text: String,
    titleText: String,
    bottomText: String,
    onValueChange: (String) -> Unit,
    imageStart: ImageVector? = null,
    imageEnd: ImageVector? = null,
    hasFocus: Boolean = false,
    hasError: Boolean = false,
    typeText: EnumEditTextType = EnumEditTextType.DEFAULT,
    maxLength: Int? = null,
) {
    val colors = LocalCustomColors.current
    val focusRequester = remember { FocusRequester() }

    val isPasswordVisible = remember {
        mutableStateOf(false)
    }

    var textState by remember {
        mutableStateOf("")
    }

    textState = text

    var visualInformation = VisualTransformation.None

    when (typeText) {
        EnumEditTextType.PASSWORD -> {
            visualInformation = if (isPasswordVisible.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        }

        EnumEditTextType.DEFAULT, EnumEditTextType.EMAIL, EnumEditTextType.NUMBER, EnumEditTextType.LONG_NUMBER -> {
            VisualTransformation.None
        }
    }

    if (hasFocus) {
        val lifecycleOwner = LocalLifecycleOwner.current
        val keyboardController = LocalSoftwareKeyboardController.current
        ManageKeyboardFocus(
            focusRequester = focusRequester,
            lifecycleOwner = lifecycleOwner,
            keyboardController = keyboardController
        )
    }
    Column {
        Spacer(modifier = Modifier.height(size5dp))
        Text(
            text = titleText,
            modifier = Modifier.padding(start = size5dp),
            color = colors.colorPrimaryText
        )
        Spacer(modifier = Modifier.height(size5dp))
        ConstraintLayout(
            modifier = Modifier
                .border(
                    width = borderStrokeMedium,
                    color = if (hasError) {
                        Color.Red
                    } else {
                        colors.colorNeutralBorder
                    },
                    shape = RoundedCornerShape(cornerRadius)
                )
                .background(color = Color.Unspecified)
                .height(size48dp)
                .fillMaxWidth()
        ) {
            val (imageS, textF, imageE) = createRefs()
            imageStart?.let { image ->
                Icon(
                    imageVector = image,
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(imageS) {
                            start.linkTo(parent.start, margin = size15dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(size24dp),
                    tint = colors.colorBgIcon
                )
            }
            val modifierText = Modifier.constrainAs(textF) {
                imageStart?.let {
                    start.linkTo(imageS.end, margin = size15dp)
                } ?: run {
                    start.linkTo(parent.start, margin = size15dp)
                }
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                if (typeText != EnumEditTextType.PASSWORD) {
                    imageEnd?.let {
                        end.linkTo(imageE.start)
                    } ?: run {
                        end.linkTo(parent.end, margin = size15dp)
                    }
                } else {
                    end.linkTo(imageE.start)
                }
                width = Dimension.fillToConstraints
            }
            BasicTextField(
                value = textState,
                onValueChange = { newText ->
                    maxLength?.let {
                        if (newText.length <= it) {
                            textState = newText
                        }
                    } ?: run {
                        textState = newText
                    }
                    onValueChange(newText)
                },
                modifier = if (hasFocus) {
                    modifierText
                        .focusRequester(focusRequester = focusRequester)
                } else {
                    modifierText
                },
                singleLine = true,
                maxLines = 1,
                textStyle = TextStyle(color = colors.colorPrimaryText),
                keyboardOptions = getKeyboardOptions(typeText),
                visualTransformation = visualInformation
            )
            if (typeText != EnumEditTextType.PASSWORD) {
                imageEnd?.let { image ->
                    Icon(
                        imageVector = image,
                        contentDescription = null,
                        modifier = Modifier
                            .constrainAs(imageE) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end, margin = size15dp)
                                start.linkTo(textF.end, margin = size15dp)
                            }
                            .size(size24dp),
                        tint = colors.colorBgIcon
                    )
                }
            } else {
                Icon(
                    imageVector = if (isPasswordVisible.value) {
                        ImageVector.vectorResource(R.drawable.ic_show_password)
                    } else {
                        ImageVector.vectorResource(R.drawable.ic_hide_password)
                    },
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(imageE) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, margin = size15dp)
                            start.linkTo(textF.end, margin = size15dp)
                        }
                        .clickable {
                            if (typeText == EnumEditTextType.PASSWORD) {
                                isPasswordVisible.value = !isPasswordVisible.value
                            }
                        }
                        .size(size24dp),
                    tint = colors.colorBgIcon
                )
            }
        }
        Spacer(modifier = Modifier.height(size5dp))
        Text(
            text = bottomText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = size5dp),
            textAlign = TextAlign.End,
            color = if (hasError) {
                Color.Red
            } else {
                colors.colorPrimaryText
            }
        )
        Spacer(modifier = Modifier.height(size5dp))
    }
}

fun getKeyboardOptions(typeText: EnumEditTextType): KeyboardOptions {
    return when (typeText) {
        EnumEditTextType.PASSWORD -> {
            KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        }

        EnumEditTextType.EMAIL -> {
            KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            )
        }

        EnumEditTextType.DEFAULT -> {
            KeyboardOptions()
        }

        EnumEditTextType.NUMBER, EnumEditTextType.LONG_NUMBER -> {
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        }
    }
}

@Composable
fun ManageKeyboardFocus(
    focusRequester: FocusRequester,
    lifecycleOwner: LifecycleOwner,
    keyboardController: SoftwareKeyboardController?,
) {
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    focusRequester.requestFocus()
                    keyboardController?.show()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    keyboardController?.hide()
                }

                else -> {
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestCustomEditText() {
    val txt = remember {
        mutableStateOf("Esto es una prueba, Esto es una prueba, Esto es una prueba, Esto es una prueba, Esto es una prueba, Esto es una prueba")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CustomEditText(
            text = txt.value,
            onValueChange = { txt.value = it },
            hasFocus = false,
            hasError = false,
            titleText = "Nombre de usuario",
            bottomText = "Prueba texto abajo",
            imageStart = ImageVector.vectorResource(R.drawable.ic_email),
            typeText = EnumEditTextType.DEFAULT
        )
    }
}
