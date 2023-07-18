package com.yihao.library.extensions

import android.graphics.Matrix
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yihao.library.utils.toOffset

fun Modifier.streamerBorder(
    borderWidth: Dp = 4.dp,
    borderShape: Shape,
    colorList: List<Color>,
    colorStops: List<Float>? = null,
    tileMode: TileMode = TileMode.Clamp,
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition()
    val degree by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 0,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    val animBrush = remember(degree) {
        val matrix = Matrix()
        mutableStateOf(
            object : ShaderBrush() {
                override fun createShader(size: Size): Shader {
                    return LinearGradientShader(
                        from = Offset.Zero, to = size.toOffset(),
                        colors = colorList,
                        colorStops = colorStops, tileMode = tileMode
                    ).also {
                        matrix.apply {
                            reset()
                            setRotate(degree, size.center.x, size.center.y)
                            it.setLocalMatrix(this)
                        }
                    }
                }
            }
        )
    }
    then(
        Modifier.border(
            brush = animBrush.value, width = borderWidth, shape = borderShape
        )
    )
}

@Preview
@Composable
fun TestStreamerBorder() {
    Box(
        modifier = Modifier
            .size(300.dp)
            .clip(RectangleShape)
            .background(Color.White)
            .streamerBorder(
                borderShape = RectangleShape, colorList = listOf(
                    Color.Red,
                    Color.Green,
                    Color.Cyan
                ),
                borderWidth = 8.dp
            )
    )
}