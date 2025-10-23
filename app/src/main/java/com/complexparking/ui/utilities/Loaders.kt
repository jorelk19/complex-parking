package com.complexparking.ui.utilities

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import com.complexparking.ui.base.CustomTextMedium
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
    modifier: Modifier = Modifier,
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
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.colorPrimaryBg)
                .alpha(65f),
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

object LinearProgressManager {
    private val isLoading = mutableStateOf(false)
    private val progress = mutableFloatStateOf(0f)

    fun showLoader() {
        isLoading.value = true
    }

    fun hideLoader() {
        isLoading.value = false
        progress.floatValue = 0f
    }

    fun loaderState(): MutableState<Boolean> {
        return isLoading
    }

    fun updateProgress(counter: Float) {
        progress.floatValue = counter
    }

    fun progressStatus(): MutableState<Float> {
        return progress
    }
}

@Composable
fun AnimateLinearProgressBarControl() {
    val isLoading by LinearProgressManager.loaderState()
    val progress by LinearProgressManager.progressStatus()
    //var loading by remember { mutableStateOf(false) }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000), label = "progressAnimation"
    )
    if (isLoading) {
        Column(modifier = Modifier.fillMaxWidth()) {
            /*Button(onClick = {
                loading = true
                // Simulate a loading process
                // In a real app, this would be actual work
                // LaunchedEffect or coroutines are often used here
                // to update the progress value
                // For demonstration, we'll just set it to 1.0f after a delay
                // In a real scenario, you would update `progress` incrementally
                progress = 1.0f
            }, enabled = !loading) {
                Text("Start Loading")
            }*/
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier.fillMaxWidth(),
                color = ProgressIndicatorDefaults.linearColor,
                trackColor = ProgressIndicatorDefaults.linearTrackColor,
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )
            if (animatedProgress == 100f) {
                CustomTextMedium("Loading Complete!")
                LinearProgressManager.hideLoader() // Reset loading state
            }
        }
    }
}