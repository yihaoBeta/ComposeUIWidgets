package com.yihao.library.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SwitchPageLayout(
    modifier: Modifier = Modifier,
    switchDirection: SwitchDirection = SwitchDirection.VERTICAL,
    autoSwitch: Boolean = true,
    duration: Int = 800,
    delayMillis: Long = 2000,
    onClick: (Int) -> Unit = {},
    content: @Composable () -> Unit
) {
    //所有待显示的控件数量
    var count = remember { 0 }
    //当前显示的控件编号
    var current by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = autoSwitch, key2 = count, block = {
        if (autoSwitch && count > 0) {
            (0 until count).asSequence()
                .repeat()
                .asFlow()
                .filter { current != it }
                .onEach {
                    delay(delayMillis)
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    current = it
                }
        }
    })
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        AnimatedContent(targetState = current, transitionSpec = {
            addAnimate(
                duration = duration,
                direction = switchDirection
            ).using(sizeTransform = SizeTransform(clip = true))
        }) { number ->
            Layout(content = content, modifier = Modifier.clickable {
                onClick.invoke(number)
            }) { measurables, constraints ->
                val placeables = measurables.map {
                    it.measure(constraints)
                }.also {
                    count = it.size
                }
                //取子控件的最大宽高
                val width = placeables.maxBy { it.width }.width.coerceAtMost(constraints.maxWidth)
                val height =
                    placeables.maxBy { it.height }.height.coerceAtMost(constraints.maxHeight)
                layout(width, height) {
                    placeables[number].let {
                        //设置居中显示
                        val x = (width - it.width) / 2
                        val y = (height - it.height) / 2
                        it.placeRelative(x, y)
                    }
                }
            }
        }
    }
}

enum class SwitchDirection {
    HORIZONTAL,
    VERTICAL
}

fun <T> Sequence<T>.repeat(enable: Boolean = true) =
    sequence {
        while (enable) {
            yieldAll(this@repeat)
        }
    }


@ExperimentalAnimationApi
fun addAnimate(
    duration: Int = 800,
    direction: SwitchDirection = SwitchDirection.VERTICAL
): ContentTransform {
    val anim = if (direction == SwitchDirection.VERTICAL) {
        slideInVertically(animationSpec = tween(durationMillis = duration)) { fullHeight -> fullHeight } + fadeIn(
            animationSpec = tween(durationMillis = duration)
        ) with
                slideOutVertically(animationSpec = tween(durationMillis = duration)) { fullHeight -> -fullHeight } + fadeOut(
            animationSpec = tween(durationMillis = duration)
        )
    } else {
        slideInHorizontally(animationSpec = tween(durationMillis = duration)) { fullWidth -> fullWidth } + fadeIn(
            animationSpec = tween(durationMillis = duration)
        ) with
                slideOutHorizontally(animationSpec = tween(durationMillis = duration)) { fullWidth -> -fullWidth } + fadeOut(
            animationSpec = tween(durationMillis = duration)
        )
    }
    return anim
}

@Preview
@Composable
fun TestSwitchPageLayout() {
    SwitchPageLayout(onClick = {}) {
        Text(text = "Hello")
        Text(text = "world")
        Text(text = "你好")
        Text(text = "世界")
    }
}

