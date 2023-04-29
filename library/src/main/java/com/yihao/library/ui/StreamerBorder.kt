package com.yihao.library.ui

import android.graphics.Matrix
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun StreamerBorder(
    borderWidth: Dp = 4.dp,
    borderShape: Shape,
    colorList: List<Color>,
    colorStops: List<Float>? = null,
    tileMode: TileMode = TileMode.Clamp,
    content: @Composable () -> Unit
) {
    val degree by rememberInfiniteTransition().animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val matrix = Matrix()
    val density = LocalDensity.current
    Layout(
        modifier = Modifier.drawWithContent {
            val shader = object : ShaderBrush() {
                override fun createShader(size: Size): Shader {
                    return LinearGradientShader(
                        colors = colorList,
                        colorStops = colorStops,
                        from = Offset.Zero,
                        to = Offset(size.width, size.height),
                        tileMode = tileMode
                    ).also {
                        matrix.reset()
                        matrix.setRotate(degree, size.center.x, size.center.y)
                        it.setLocalMatrix(matrix)
                    }
                }
            }
            drawContent()
            drawIntoCanvas {
                val gap = borderWidth.toPx()
                val outline = borderShape.createOutline(
                    Size(size.width - gap, size.height - gap),
                    layoutDirection,
                    density
                )
                it.translate(gap / 2, gap / 2)
                drawOutline(
                    outline,
                    brush = shader,
                    style = Stroke(width = borderWidth.toPx(), join = StrokeJoin.Round)
                )
            }
        },
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map {
            it.measure(constraints)
        }
        val width = min(
            placeables.maxBy { it.width }.width + borderWidth.toPx() * 2,
            constraints.maxWidth.toFloat()
        )
        val height = min(
            placeables.maxBy { it.height }.height + borderWidth.toPx() * 2,
            constraints.maxHeight.toFloat()
        )
        layout(width.toInt(), height.toInt()) {
            placeables.forEach {
                it.placeRelative(IntOffset(borderWidth.toPx().toInt(), borderWidth.toPx().toInt()))
            }
        }
    }
}

@Preview
@Composable
fun PrevStreamerBorder() {
    StreamerBorder(
        borderShape = RoundedCornerShape(50),
        colorList = listOf(Color.Red, Color.Green, Color.Cyan)
    ) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .aspectRatio(2f)
                .clip(shape = RoundedCornerShape(50))
                .background(color = Color.White)
        )
    }
}