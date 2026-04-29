package com.maheswara660.vidora.core.ui.components

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun IosLoader(
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    size: Int = 40
) {
    val loaderColor = if (color == Color.Unspecified) MaterialTheme.colorScheme.primary else color
    val infiniteTransition = rememberInfiniteTransition(label = "iosLoader")
    
    Box(
        modifier = modifier.size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        repeat(8) { index ->
            Dot(
                index = index,
                totalDots = 8,
                color = loaderColor,
                size = size,
                infiniteTransition = infiniteTransition
            )
        }
    }
}

@Composable
private fun Dot(
    index: Int,
    totalDots: Int,
    color: Color,
    size: Int,
    infiniteTransition: InfiniteTransition
) {
    val delay = (index * 125) // total duration is approx 1000ms
    val duration = 1000
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration / 2, delayMillis = delay),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotScale_$index"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration / 2, delayMillis = delay),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotAlpha_$index"
    )

    Box(
        modifier = Modifier
            .size(size.dp)
            .graphicsLayer {
                rotationZ = index * (360f / totalDots)
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .size((size / 5).dp)
                .scale(scale)
                .alpha(alpha)
                .background(color, CircleShape)
        )
    }
}
