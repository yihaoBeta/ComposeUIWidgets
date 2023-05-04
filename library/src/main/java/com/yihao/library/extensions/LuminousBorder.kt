package com.yihao.library.extensions

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


fun Modifier.luminousBorder(
    shape: Shape,
    borderColor: Color = Color.Red,
    borderWidth: Dp = 4.dp,
    radius: Float = 10f,
): Modifier {
    return then(
        Modifier
            .drawWithCache {
                val strokeWidthPx = borderWidth.toPx()
                val outline = shape.createOutline(size, layoutDirection, this)
                onDrawWithContent {
                    drawIntoCanvas {
                        drawContent()
                        val paint = Paint().apply {
                            this.color = borderColor
                            strokeWidth = strokeWidthPx
                            isAntiAlias = true
                            style = PaintingStyle.Stroke
                            blendMode = BlendMode.Src
                            this.asFrameworkPaint().maskFilter =
                                BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL)
                        }
                        it.drawOutline(outline, paint)
                    }
                }
            })
}

@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true, backgroundColor = 0xFF445544)
@Composable
fun Test() {

    Box(
        modifier = Modifier
            .size(300.dp)
            .luminousBorder(radius = 5f, borderWidth = 4.dp, shape = RoundedCornerShape(50))
            .aspectRatio(3f)
            .clip(RoundedCornerShape(50))
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello world", fontSize = 40.sp)
    }

}