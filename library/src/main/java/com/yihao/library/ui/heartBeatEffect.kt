package com.yihao.library.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yihao.library.utils.plus

/**
 * 心跳动画效果,布局外边框跳动
 * @author yihao
 * @param expandLength:动画向外扩展的长度
 * @param borderShape:边框形状
 * @param borderColor:边框颜色
 * @param borderWidth:边框宽度
 * @param duration:单次跳动持续时间
 * @param delayMillis:两次跳动之间的延迟时间
 * @param content:包裹内容
 */
@Composable
fun HeartBeatOfBorder(
    modifier: Modifier = Modifier,
    expandLength: Dp,
    borderShape: Shape,
    borderColor: Color = Color.Red,
    borderWidth: Dp = 2.dp,
    duration: Int = 800,
    delayMillis: Int = 300,
    content: @Composable () -> Unit
) {
    require(expandLength > borderWidth)
    val factor by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = duration, delayMillis = delayMillis),
            RepeatMode.Reverse
        )
    )
    val density = LocalDensity.current
    Box(
        modifier = modifier
            .padding(expandLength)
            .drawWithCache {
                val expandLengthValue = expandLength.toPx()
                val borderWidthValue = borderWidth.toPx()
                val halfBorderWidth = borderWidthValue / 2
                onDrawBehind {
                    val gap = (expandLengthValue - borderWidthValue) * factor
                    val outline = borderShape.createOutline(
                        size = (size + Size(gap, gap) * 2f) + borderWidthValue,
                        layoutDirection,
                        density
                    )

                    translate(-gap - halfBorderWidth, -gap - halfBorderWidth) {
                        drawOutline(
                            outline,
                            color = borderColor,
                            style = Stroke(width = borderWidthValue)
                        )
                    }
                }
            }, contentAlignment = Alignment.Center
    ) {
        content()
    }
}

/**
 * 整体内容仿心跳效果，请在设置背景之前调用
 * @author yihao
 * @param scaleFactor:缩放因数，介于0-1之间
 * @param duration:单次跳动持续时间
 * @param delayMillis:两次跳动之间的时间间隔
 */
fun Modifier.heartBeatOfContent(
    scaleFactor: Float = 0.8f,
    duration: Int = 800,
    delayMillis: Int = 300
): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val scale by transition.animateFloat(
        initialValue = scaleFactor,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration,
                delayMillis = delayMillis,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    this.then(Modifier.scale(scale))
}


@Preview
@Composable
fun TestPrev() {
    HeartBeatOfBorder(
        modifier = Modifier
            .size(300.dp)
            .aspectRatio(2.5f),
        expandLength = 20.dp,
        borderWidth = 4.dp,
        borderShape = RoundedCornerShape(50)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(50))
                .background(color = Color.Green)
        )
    }
}