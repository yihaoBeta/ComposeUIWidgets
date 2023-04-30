package com.yihao.library.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

/**
 * Size + 重载
 */
operator fun Size.plus(other: Size) =
    Size(this.width + other.width, this.height + other.height)

/**
 * Size - 重载
 */
operator fun Size.minus(value: Float) = Size(this.width - value, this.height - value)

/**
 * Size + 一个数
 */
operator fun Size.plus(value: Float) = Size(this.width + value, this.height + value)

/**
 * 舍弃Size中的高度信息
 */
fun Size.onlyWidth() = this.copy(height = 0f)

/**
 * 舍弃Size中的宽度信息
 */
fun Size.onlyHeight() = this.copy(width = 0f)

/**
 * Size转化成Offset
 */
fun Size.toOffset() = Offset(this.width, this.height)