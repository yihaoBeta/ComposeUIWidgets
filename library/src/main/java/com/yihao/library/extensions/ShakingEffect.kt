package com.yihao.library.extensions

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 定义动画的方向
 */
enum class ShakeDirection {
    HORIZONTAL,
    VERTICAL
}

/**
 * 控件抖动效果，多用于错误提示
 * @author yihao
 * @param animate:动画触发开关，此值改变后触发一次动画
 * @param maxDp:抖动的最大距离
 * @param duration:动画持续时间
 * @param direction:动画方向
 * @see ShakeDirection
 */
fun Modifier.shakingEffect(
    animate: Boolean = false,
    maxDp: Dp = 10.dp,
    duration: Int = 300,
    direction: ShakeDirection = ShakeDirection.HORIZONTAL
): Modifier = composed {
    var shake by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = animate, block = {
        shake = animate
    })
    val transition = updateTransition(targetState = shake, label = "shake")
    val shakeOffset by transition.animateDp(
        transitionSpec = {
            keyframes {
                durationMillis = duration
                0.dp atFraction 0f
                (-maxDp) atFraction 0.083f
                0.dp atFraction 0.167f
                maxDp atFraction 0.25f
                0.dp atFraction 0.334f
                (-maxDp.value * 0.8f).dp atFraction 0.417f
                0.dp atFraction 0.5f
                (maxDp.value * 0.8f).dp atFraction 0.583f
                0.dp atFraction 0.667f
                (-maxDp.value * 0.5f).dp atFraction 0.75f
                0.dp atFraction 0.833f
                (maxDp.value * 0.5f).dp atFraction 0.917f
                0.dp atFraction 1f
            }
        }, label = "shakeOffset"
    ) {
        if (it)
            0.dp
        else
            0.dp
    }

    this.then(
        Modifier.offset(
            x = if (direction == ShakeDirection.HORIZONTAL) shakeOffset else 0.dp,
            y = if (direction == ShakeDirection.VERTICAL) shakeOffset else 0.dp
        )
    )
}