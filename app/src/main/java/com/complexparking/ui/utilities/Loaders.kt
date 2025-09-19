package com.complexparking.ui.utilities

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.Dimensions.size60dp
import com.complexparking.ui.theme.ColorBlue6
import com.complexparking.ui.theme.LocalCustomColors

object LoadingManager {
    private val isLoading = mutableStateOf(false)

    fun showLoader() {
        isLoading.value = true
    }

    fun hideLoader() {
        isLoading.value = false
    }

    fun loaderState(): MutableState<Boolean> {
        return isLoading
    }
}

@Composable
fun PulseLoader(
    modifier: Modifier = Modifier
) {
    val isLoading by LoadingManager.loaderState()

    if (isLoading) {
        val colors = LocalCustomColors.current
        val infiniteTransition = rememberInfiniteTransition(label = "Pulse loader")
        val progress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000)
            ),
            label = "progress animation"
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(size60dp)
                    .graphicsLayer {
                        scaleX = progress
                        scaleY = progress
                        alpha = 1f - progress
                    }
                    .border(
                        width = size5dp,
                        color = ColorBlue6,
                        shape = CircleShape
                    )
            )
        }
    }
}