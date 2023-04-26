package com.yihao.library.extensions

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate

fun Modifier.infiniteRotate(): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition()
    val rotate by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            RepeatMode.Restart
        )
    )
    this.then(Modifier.rotate(rotate))
}