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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min

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
    expandLength: Dp,
    borderShape: Shape,
    borderColor: Color = Color.Red,
    borderWidth: Dp = 2.dp,
    duration: Int = 800,
    delayMillis: Int = 300,
    content: @Composable () -> Unit
) {
    val factor by rememberInfiniteTransition().animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = duration, delayMillis = delayMillis),
            RepeatMode.Reverse
        )
    )
    val density = LocalDensity.current
    Layout(
        modifier = Modifier.drawWithContent {
            drawContent()
            drawIntoCanvas {
                val gap = expandLength.toPx() * factor
                val outline = borderShape.createOutline(
                    Size(size.width - gap * 2, size.height - gap * 2),
                    layoutDirection,
                    density
                )
                it.translate(gap, gap)
                drawOutline(
                    outline,
                    color = borderColor,
                    style = Stroke(width = borderWidth.toPx(), join = StrokeJoin.Round)
                )
            }
        },
        content = content
    ) { measurables, constraints ->
        val placeableList = measurables.map {
            it.measure(constraints)
        }
        val width = min(
            placeableList.maxBy { it.width }.width + (expandLength.toPx() * 2),
            constraints.maxWidth.toFloat()
        )
        val height = min(
            placeableList.maxBy { it.height }.height + (expandLength.toPx() * 2),
            constraints.maxHeight.toFloat()
        )

        layout(width.toInt(), height.toInt()) {
            placeableList.forEach {
                it.placeRelative(expandLength.toPx().toInt(), expandLength.toPx().toInt())
            }
        }
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
    Box(
        modifier = Modifier
            .heartBeatOfContent()
            .size(100.dp, 50.dp)
            .background(color = Color.Red, shape = RoundedCornerShape(5.dp))
    )
}