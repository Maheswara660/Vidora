package com.maheswara660.vidora.feature.player.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.maheswara660.vidora.core.model.DoubleTapGesture
import com.maheswara660.vidora.core.ui.R
import com.maheswara660.vidora.core.ui.theme.VidoraTheme
import com.maheswara660.vidora.feature.player.state.TapGestureState

@Composable
fun DoubleTapIndicator(modifier: Modifier = Modifier, tapGestureState: TapGestureState) {
    if (tapGestureState.seekMillis == 0L) return
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = if (tapGestureState.seekMillis > 0) Alignment.CenterEnd else Alignment.CenterStart,
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(
                    fraction = when (tapGestureState.doubleTapGesture) {
                        DoubleTapGesture.PLAY_PAUSE -> 0f
                        DoubleTapGesture.FAST_FORWARD_AND_REWIND -> 0.5f
                        DoubleTapGesture.BOTH -> 0.35f
                        DoubleTapGesture.NONE -> 0f
                    },
                )
                .clip(if (tapGestureState.seekMillis > 0) RightSideOvalShape() else LeftSideOvalShape())
                .background(Color.White.copy(0.2f))
                .indication(tapGestureState.interactionSource, ripple()),
            contentAlignment = Alignment.Center,
        ) {
            val animationDuration = 500L
            val alpha = remember { Animatable(0f) }
            val scale = remember { Animatable(0.8f) }

            LaunchedEffect(tapGestureState.seekMillis) {
                if (tapGestureState.seekMillis != 0L) {
                    alpha.snapTo(0f)
                    scale.snapTo(0.8f)
                    alpha.animateTo(1f, animationSpec = tween(100))
                    scale.animateTo(1.2f, animationSpec = tween(200))
                    alpha.animateTo(0f, animationSpec = tween(200))
                }
            }

            Icon(
                painter = painterResource(R.drawable.ic_fast),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .graphicsLayer {
                        this.alpha = alpha.value
                        this.scaleX = scale.value
                        this.scaleY = scale.value
                        this.rotationZ = if (tapGestureState.seekMillis > 0) 0f else 180f
                    },
                tint = Color.White,
            )
        }
    }
}


private class RightSideOvalShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path().apply {
            moveTo(size.width, size.height)
            lineTo(size.width, 0f)
            lineTo(size.width * 0.1f, 0f)
            cubicTo(
                size.width * 0.1f,
                0f,
                -size.width * 0.1f,
                size.height / 2,
                size.width * 0.1f,
                size.height,
            )
            close()
        }
        return Outline.Generic(path)
    }
}

private class LeftSideOvalShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width * 0.9f, 0f)
            cubicTo(
                size.width * 0.9f,
                0f,
                size.width * 1.1f,
                size.height / 2,
                size.width * 0.9f,
                size.height,
            )
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}


@Preview
@Composable
private fun OvalShapePreview() {
    VidoraTheme {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(LeftSideOvalShape())
                    .background(Color.Blue),
            ) {}
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(RightSideOvalShape())
                    .background(Color.Blue),
            ) {}
        }
    }
}
