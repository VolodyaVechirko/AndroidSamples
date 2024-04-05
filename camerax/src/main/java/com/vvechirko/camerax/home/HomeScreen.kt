package com.vvechirko.camerax.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vvechirko.camerax.R
import com.vvechirko.camerax.ui.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    photos: List<Photo> = getPhotos(LocalContext.current),
    onPostClicked: (String) -> Unit = { }
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
//    val tabItems = listOf(Icons.Filled.DateRange, Icons.Filled.AccountBox, Icons.Filled.Favorite)
    val tabItems = listOf("Grids", "Vertical", "Likes")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                Log.d("App", "tabPositions $tabPositions")
//                SimpleTabIndicator(
//                    color = MaterialTheme.colorScheme.primary,
//                    currentTabPosition = tabPositions[pagerState.currentPage]
//                )
                AnimatedDashIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    selectedTabIndex = pagerState.currentPage,
                    tabPositions = tabPositions
                )
            },
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = (pagerState.currentPage == index),
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
//                    icon = { Icon(imageVector = item, contentDescription = null) },
                    text = { Text(text = item, style = MaterialTheme.typography.titleMedium) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        HorizontalPager(pageCount = tabItems.size, state = pagerState) { page ->
            when (page) {
                0 -> LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(photos) { photo ->
                        PostGridItem(photo.url, onItemClicked = { onPostClicked(photo.id) })
                    }
                }

                1 -> LazyColumn {
                    items(photos) { photo ->
                        PostListItem(photo.url, onItemClicked = { onPostClicked(photo.id) })
                    }
                }

                else -> Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "No favorite photos",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun PostListItem(filePath: String, onItemClicked: (() -> Unit)? = null) {
    Column(modifier = if (onItemClicked == null) Modifier else Modifier.clickable { onItemClicked() }) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(R.drawable.ana),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = "Annastasiia",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Online",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        PostPhotoView(filePath, 8.dp)
    }
}

@Composable
fun PostGridItem(filePath: String, onItemClicked: (() -> Unit)? = null) {
    Box(modifier = if (onItemClicked == null) Modifier else Modifier.clickable { onItemClicked() }) {
        PostPhotoView(filePath, 2.dp)
    }
}

@Composable
fun PostPhotoView(filePath: String, padding: Dp = 2.dp) {
    Surface(
        shape = MaterialTheme.shapes.small,
        shadowElevation = 2.dp,
        modifier = Modifier.padding(all = padding)
    ) {
        if (LocalInspectionMode.current) {
            Image(
                painter = painterResource(R.drawable.ana),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        } else {
            AsyncImage(
                model = filePath,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Preview(device = Devices.PIXEL_3)
@Composable
fun PreviewPostCard() {
    AppTheme {
        Surface {
            PostListItem("")
        }
    }
}

//@Preview(device = Devices.PIXEL_3)
//@Composable
//fun PreviewHomeScreen() {
//    AppTheme {
//        HomeScreen(photos = emptyList())
//    }
//}
