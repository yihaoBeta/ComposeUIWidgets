package com.yihao.library.extensions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

/**
 * 使控件带有按压效果
 * @author yihao
 * @param minScale:最小的缩放值(0f-1f)
 */
fun Modifier.pressEffect(minScale: Float = 0.7f): Modifier = composed {
    var scale by remember {
        mutableStateOf(1.0f)
    }
    val scaleState by animateFloatAsState(
        targetValue = scale,
        animationSpec = spring()
    )
    this.then(
        Modifier
            .pointerInput(Unit) {
                forEachGesture {
                    //全部手指离开控件，恢复初始状态
                    scale = 1.0f
                    awaitPointerEventScope {
                        val event = awaitPointerEvent(PointerEventPass.Main)
                        if (event.type == PointerEventType.Press) {//检测到按压操作，执行缩放
                            scale = minScale
                        }
                    }
                }
            }
            .scale(scaleState))
}