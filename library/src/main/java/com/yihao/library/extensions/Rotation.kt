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

/**
 * 无限旋转效果
 * @param speed 旋转速度,单位圈/秒
 */
fun Modifier.infiniteRotate(speed: Double = 1.0): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition()
    val rotate by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = (1000.0 / speed).toInt(), easing = LinearEasing),
            RepeatMode.Restart
        )
    )
    this.then(Modifier.rotate(rotate))
}