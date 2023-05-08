package com.yihao.library.ui

import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import android.graphics.Color as AndroidColor

/**
 * 仿翻页时钟效果的翻页文本组件
 * 当传入的to参数发生改变时，自动执行翻页动画
 * @author yihao
 * @param fontSize:字号
 * @param from:原字符串
 * @param to:待动画跳转的字符串
 * @param backgroundColor:背景色
 * @param textColor:文本颜色
 * @param textTypeface:文本字体
 * @param dividerColor:中间分隔符颜色
 * @param dividerWidth:中间分隔符宽度，传入0dp时为无分割线
 */
@Composable
fun FlipTextView(
    modifier: Modifier = Modifier,
    fontSize: TextUnit,
    from: String,
    to: String,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    textTypeface: Typeface = Typeface.DEFAULT,
    dividerWidth: Dp = 4.dp,
    dividerColor: Color = Color.White
) {
    val camera = remember { Camera() }
    val matrix = remember { Matrix() }
    val density = LocalDensity.current
    //分割线宽度dp转px
    val dividerWidthPx = remember { with(density) { dividerWidth.toPx() } }
    //文本画笔
    val textPaint = Paint().also {
        it.color = AndroidColor.argb(
            textColor.alpha,
            textColor.red,
            textColor.green,
            textColor.blue
        )
        it.typeface = textTypeface
        it.textSize = with(density) { fontSize.toPx() }
        it.textAlign = Paint.Align.CENTER
    }
    //阴影画笔，用来调整绘制区域的alpha值
    val shadowPaint = Paint().also {
        it.color = AndroidColor.argb(
            backgroundColor.alpha,
            backgroundColor.red,
            backgroundColor.green,
            backgroundColor.blue
        )
        it.style = Paint.Style.FILL
    }
    //动画持续的时间，nanoMillis
    var playTime by remember { mutableStateOf(0L) }
    //当前旋转的角度，0-180度
    var progress by remember { mutableStateOf(0f) }
    //动画效果执行体
    val animation = remember {
        TargetBasedAnimation(
            animationSpec = tween(durationMillis = 600),
            Float.VectorConverter,
            initialValue = 0f,
            targetValue = 180f
        )
    }
    var flipEnd = false//动画是否执行完毕，用于解决多线程并发导致的闪屏问题(LaunchEffect线程与onDraw线程)
    LaunchedEffect(key1 = to, block = {
        val startTime = withFrameNanos { it }
        do {
            playTime = withFrameNanos { it } - startTime
            progress = animation.getValueFromNanos(playTime)
        } while (!animation.isFinishedFromNanos(playTime))
        flipEnd = true
        progress = 0f
    })
    Canvas(modifier = modifier
        .aspectRatio(1f)
        .background(backgroundColor),
        onDraw = {
            drawContext.canvas.nativeCanvas.let { canvas ->
                //绘制上半部分
                clipRect(0f, 0f, size.width, size.height / 2) {
                    canvas.drawText(to, textPaint, size)
                }
                //绘制下半部分
                clipRect(0f, size.height / 2, size.width, size.height) {
                    if (flipEnd)
                        canvas.drawText(to, textPaint, size)
                    else
                        canvas.drawText(from, textPaint, size)
                }
                //绘制翻转动画部分
                if (!flipEnd) {
                    if (progress <= 90f) {
                        clipRect(0f, 0f, size.width, size.height / 2) {
                            camera.save()
                            camera.rotateX(-progress)
                            camera.getMatrix(matrix)
                            matrix.position(size)
                            canvas.concat(matrix)
                            shadowPaint.alpha = ((90 - progress) / 90 * 255f).toInt()
                            canvas.drawRect(0f, 0f, size.width, size.height / 2, shadowPaint)
                            canvas.drawText(from, textPaint, size)
                            camera.restore()
                        }
                    } else {
                        clipRect(0f, size.height / 2, size.width, size.height) {
                            camera.save()
                            camera.rotateX(-(progress - 180f))
                            camera.getMatrix(matrix)
                            matrix.position(size)
                            canvas.concat(matrix)
                            shadowPaint.alpha = ((progress - 90) / 90 * 255).toInt()
                            canvas.drawRect(
                                0f,
                                size.height / 2,
                                size.width,
                                size.height,
                                shadowPaint
                            )
                            canvas.drawText(to, textPaint, size)
                            camera.restore()
                        }
                    }
                }
                //绘制分割线
                if (dividerWidthPx > 0) {
                    canvas.drawRect(
                        0f,
                        size.height / 2 - dividerWidthPx / 2,
                        size.width,
                        size.height / 2 + dividerWidthPx / 2,
                        Paint().also {
                            it.color = AndroidColor.argb(
                                dividerColor.alpha,
                                dividerColor.red,
                                dividerColor.green,
                                dividerColor.blue
                            )
                        })
                }
            }
        })
}

/**
 * 处理matrix，使得旋转匹配整个view大小
 */
internal fun Matrix.position(size: Size) {
    preScale(0.25f, 0.25f)
    postScale(4.0f, 4.0f)
    preTranslate(-size.width / 2, -size.height / 2)
    postTranslate(size.width / 2, size.height / 2)
}

/**
 * 绘制文字
 * @param text:待绘制的文字
 * @param paint:文本画笔
 * @param size:文本匹配的绘制区域大小
 */
internal fun Canvas.drawText(text: String, paint: Paint, size: Size) {
    val fontMetrics = paint.fontMetrics
    drawText(
        text,
        size.width / 2,
        size.height / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom,
        paint
    )
}

