package com.yihao.composetools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PhotoAlbum
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yihao.composetools.ui.theme.ComposeToolsTheme
import com.yihao.library.extensions.circularTransform
import com.yihao.library.extensions.infiniteRotate
import com.yihao.library.extensions.luminousBorder
import com.yihao.library.extensions.pressEffect
import com.yihao.library.extensions.shakingEffect
import com.yihao.library.extensions.streamerBorder
import com.yihao.library.ui.HeartBeatOfBorder
import com.yihao.library.ui.SwitchDirection
import com.yihao.library.ui.SwitchPageLayout
import com.yihao.library.ui.heartBeatOfContent
import com.yihao.library.ui.shinningEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeToolsTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(topBar = {
                    TopAppBar(title = { Text(text = "Compose tools sample") })
                }) {
                    LazyVerticalGrid(
                        contentPadding = PaddingValues(
                            start = 4.dp,
                            end = 4.dp,
                            top = it.calculateTopPadding()
                        ),
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        gridItem {
                            Icon(
                                modifier = Modifier
                                    .fillMaxSize(0.9f)
                                    .background(color = Color.White, shape = CircleShape)
                                    .infiniteRotate(speed = 0.5)
                                    .pressEffect(),
                                imageVector = Icons.Rounded.PhotoAlbum,
                                contentDescription = null,
                                tint = Color.Blue
                            )
                        }
                        gridItem {
                            Button(modifier = Modifier
                                .pressEffect(),
                                onClick = {
                                }) {
                                Text(text = "按压我试试", overflow = TextOverflow.Ellipsis)
                            }
                        }
                        gridItem {
                            var toggle by remember {
                                mutableStateOf(true)
                            }

                            Button(modifier = Modifier
                                .shakingEffect(toggle), onClick = {
                                toggle = toggle.not()
                            }) {
                                Text(text = "点我抖动", overflow = TextOverflow.Ellipsis)
                            }
                        }
                        gridItem {
                            var change by remember {
                                mutableStateOf(false)
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .aspectRatio(2f)
                                    .background(color = Color.Green)
                                    .circularTransform(change)
                                    .clickable {
                                        change = !change
                                    }
                            )
                        }

                        gridItem {
                            HeartBeatOfBorder(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(2.5f),
                                expandLength = 15.dp,
                                borderShape = RoundedCornerShape(50),
                                borderColor = Color.White,
                                borderWidth = 4.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = Color.Red, shape = RoundedCornerShape(50)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "边框心跳效果")
                                }
                            }
                        }
                        gridItem {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(0.7f)
                                    .heartBeatOfContent()
                                    .background(color = Color.Green, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "内容心跳", color = Color.Red)
                            }
                        }

                        gridItem {
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

                        gridItem {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .aspectRatio(2.5f)
                                    .background(Color.LightGray)
                                    .shinningEffect(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "闪光效果", fontSize = 30.sp)
                            }
                        }

                        gridItem {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(0.9f)
                                    .aspectRatio(2f)
                                    .luminousBorder(radius = 15f, shape = RoundedCornerShape(50))
                                    .clip(RoundedCornerShape(50))
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "发光边框", color = Color.Black)
                            }
                        }

                        gridItem {
                            val stringList = listOf("Hello", "world", "你好", "世界")
                            val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                SwitchPageLayout(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(50))
                                        .background(Color.Blue)
                                ) {
                                    repeat(stringList.size) { index ->
                                        Box(
                                            modifier = Modifier.fillMaxWidth(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(text = stringList[index], fontSize = 26.sp)
                                        }
                                    }
                                }
                                SwitchPageLayout(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .padding(8.dp),
                                    switchDirection = SwitchDirection.HORIZONTAL
                                ) {
                                    repeat(stringList.size) { index ->
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f)
                                                .background(color = colors[index]),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = stringList[index],
                                                fontSize = 26.sp,
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 通用的gridview item
 */
fun LazyGridScope.gridItem(content: @Composable BoxScope.() -> Unit) {
    item {
        Box(
            modifier = Modifier
                .border(width = Dp.Hairline, color = Color.Yellow)
                .aspectRatio(1f)
                .background(Color.LightGray.copy(alpha = 0.5f))
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}
