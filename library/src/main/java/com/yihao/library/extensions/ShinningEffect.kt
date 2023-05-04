package com.yihao.library.extensions

import android.graphics.Matrix
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.tooling.preview.Preview
import com.yihao.library.utils.onlyWidth
import com.yihao.library.utils.toOffset

/**
 * 闪光效果
 * @author yihao
 * @param shinningColor:闪光颜色，默认白色
 * @param shinningPercent:闪光宽度占比，默认30%，取值范围[0f-1f]
 * @param duration:一次动画持续时间 默认1500ms
 * @param delayMillis:两次动画时间间隔 默认300ms
 * @param skewAngle:闪光倾斜的角度，默认30度 取值范围[0f-360f],顺时针
 */
fun Modifier.shinningEffect(
    shinningColor: Color = Color.White,
    shinningPercent: Float = 0.3f,
    duration: Int = 1500,
    delayMillis: Int = 300,
    skewAngle: Float = 30f
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = -shinningPercent,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration,
                delayMillis = delayMillis
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    val animBrush = remember(progress) {
        mutableStateOf(
            object : ShaderBrush() {
                override fun createShader(size: Size): Shader {
                    return LinearGradientShader(
                        size.onlyWidth().toOffset() * progress,
                        size.onlyWidth().toOffset() * (progress + shinningPercent),
                        colors = listOf(Color.Transparent, shinningColor, Color.Transparent)
                    ).also {
                        it.setLocalMatrix(Matrix().apply {
                            setRotate(skewAngle)
                        })
                    }
                }
            }
        )
    }
    this.then(Modifier.drawWithContent {
        drawContent()
        drawRect(
            brush = animBrush.value
        )
    })
}


@Preview
@Composable
fun TestShinning() {
    Box(
        modifier = Modifier
            .progressSemantics()
            .background(color = Color.Green),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello world", modifier = Modifier.shinningEffect())
    }
}