package com.jobswipe.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class SwipeDirection {
    LEFT, RIGHT
}

@Composable
fun SwipeableCard(
    modifier: Modifier = Modifier,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    val configuration = LocalConfiguration.current
    val screenWidth = with(LocalDensity.current) {
        configuration.screenWidthDp.dp.toPx()
    }

    val swipeThreshold = screenWidth * 0.4f

    Box(
        modifier = modifier
            .fillMaxSize()
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .graphicsLayer {
                rotationZ = (offsetX.value / screenWidth) * 15f
                alpha = 1f - (kotlin.math.abs(offsetX.value) / screenWidth) * 0.3f
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        scope.launch {
                            when {
                                offsetX.value > swipeThreshold -> {
                                    // Swipe right - animate off screen then callback
                                    offsetX.animateTo(
                                        targetValue = screenWidth * 1.5f,
                                        animationSpec = tween(300)
                                    )
                                    onSwipeRight()
                                    // Reset position
                                    offsetX.snapTo(0f)
                                    offsetY.snapTo(0f)
                                }
                                offsetX.value < -swipeThreshold -> {
                                    // Swipe left - animate off screen then callback
                                    offsetX.animateTo(
                                        targetValue = -screenWidth * 1.5f,
                                        animationSpec = tween(300)
                                    )
                                    onSwipeLeft()
                                    // Reset position
                                    offsetX.snapTo(0f)
                                    offsetY.snapTo(0f)
                                }
                                else -> {
                                    // Snap back to center
                                    launch { offsetX.animateTo(0f, tween(300)) }
                                    launch { offsetY.animateTo(0f, tween(300)) }
                                }
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                            offsetY.snapTo(offsetY.value + dragAmount.y * 0.3f)
                        }
                    }
                )
            }
    ) {
        content()
    }
}
