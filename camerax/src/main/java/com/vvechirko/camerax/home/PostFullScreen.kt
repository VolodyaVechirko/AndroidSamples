package com.vvechirko.camerax.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vvechirko.camerax.ui.AppTheme

@Composable
fun PostFullScreen(photo: Photo = Photo("stub", "stub")) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        PostListItem(photo.url)

        Row(modifier = Modifier.align(Alignment.End)) {
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Favorite, null)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Create, null)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Share, null)
            }
        }

        Text(
            modifier = Modifier.padding(all = 8.dp),
            text = "Material Design is an adaptable system of guidelines, components, and tools that support the best practices of user interface design. Backed by open-source code, Material Design streamlines collaboration between designers and developers, and helps teams quickly build beautiful products."
        )
    }
}

@Preview(device = Devices.PIXEL_3)
@Composable
fun PreviewHomeScreen() {
    AppTheme {
        Surface {
            PostFullScreen()
        }
    }
}