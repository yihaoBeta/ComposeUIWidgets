package com.yihao.composetools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PhotoAlbum
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yihao.composetools.ui.theme.ComposeToolsTheme
import com.yihao.library.extensions.infiniteRotate

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
                                    .infiniteRotate(speed = 0.5),
                                imageVector = Icons.Rounded.PhotoAlbum,
                                contentDescription = null,
                                tint = Color.Blue
                            )
                        }
                    }
                }
            }
        }
    }
}


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
