package com.yihao.library.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 文字自动适应布局大小，取最大字号显示
 * 参数请参考:
 * @see Text
 */
@Composable
fun AutoResizeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = 1,
    style: TextStyle = LocalTextStyle.current
) {
    var readyToDraw by remember {
        mutableStateOf(false)
    }
    var fontSize by remember {
        mutableStateOf(1.sp)
    }
    Text(modifier = modifier
        .drawWithContent {
            if (readyToDraw) drawContent()
        },
        text = text,
        color=color,
        fontStyle=fontStyle,
        maxLines = maxLines,
        fontSize = fontSize,
        fontWeight=fontWeight,
        fontFamily=fontFamily,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        letterSpacing = letterSpacing,
        softWrap = softWrap,
        style = style,
        onTextLayout = { layoutResult ->
            if (!readyToDraw) {
                if (layoutResult.didOverflowWidth || layoutResult.didOverflowHeight) {
                    readyToDraw = true
                    fontSize /= 1.1
                } else {
                    fontSize *= 1.1
                }
            }
        })
}

@Preview
@Composable
fun TextAutoSizeText() {
    Box(modifier = Modifier
        .size(80.dp).wrapContentHeight()) {
        AutoResizeText(fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Cursive,
            text = "HelloHelloHelloHello", textDecoration = TextDecoration.Underline,color = Color.Red)
    }
}