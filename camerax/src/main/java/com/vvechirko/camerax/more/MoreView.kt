package com.vvechirko.camerax.more

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vvechirko.camerax.ui.AppTheme

@Composable
fun MoreView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val colors = colorList()
        LazyColumn {
            items(colors) {
                Surface(color = it.second, modifier = Modifier.fillMaxWidth()) {
                    Text(text = it.first, modifier = Modifier.padding(all = 4.dp))
                }
            }
        }

        val selected = remember { mutableStateOf(false) }
        val scale = animateFloatAsState(if (selected.value) 0.8f else 1f)

        Button(
            onClick = { },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter)
                .scale(scale.value)
                .onButtonPressed { selected.value = it }
        ) {
            Text(text = "Explore Airbnb")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onButtonPressed(onPressed: (Boolean) -> Unit) = pointerInteropFilter {
    when (it.action) {
        MotionEvent.ACTION_DOWN -> {
            onPressed(true)
        }

        MotionEvent.ACTION_UP -> {
            onPressed(false)
        }
    }
    true
}

@Composable
private fun colorList(): List<Pair<String, Color>> = listOf(
    // Primary color
    "primary" to MaterialTheme.colorScheme.primary,
    "onPrimary" to MaterialTheme.colorScheme.onPrimary,
    "primaryContainer" to MaterialTheme.colorScheme.primaryContainer,
    "onPrimaryContainer" to MaterialTheme.colorScheme.onPrimaryContainer,
    "inversePrimary" to MaterialTheme.colorScheme.inversePrimary,

    // Secondary color
    "secondary" to MaterialTheme.colorScheme.secondary,
    "onSecondary" to MaterialTheme.colorScheme.onSecondary,
    "secondaryContainer" to MaterialTheme.colorScheme.secondaryContainer,
    "onSecondaryContainer" to MaterialTheme.colorScheme.onSecondaryContainer,

    // Tertiary color
    "tertiary" to MaterialTheme.colorScheme.tertiary,
    "onTertiary" to MaterialTheme.colorScheme.onTertiary,
    "tertiaryContainer" to MaterialTheme.colorScheme.tertiaryContainer,
    "onTertiaryContainer" to MaterialTheme.colorScheme.onTertiaryContainer,

    "background" to MaterialTheme.colorScheme.background,
    "onBackground" to MaterialTheme.colorScheme.onBackground,

    // Surface
    "surface" to MaterialTheme.colorScheme.surface,
    "onSurface" to MaterialTheme.colorScheme.onSurface,
    "surfaceVariant" to MaterialTheme.colorScheme.surfaceVariant,
    "onSurfaceVariant" to MaterialTheme.colorScheme.onSurfaceVariant,
    "inverseSurface" to MaterialTheme.colorScheme.inverseSurface,
    "inverseOnSurface" to MaterialTheme.colorScheme.inverseOnSurface,

    "outline" to MaterialTheme.colorScheme.outline,
)

@Preview(device = Devices.PIXEL_3)
@Composable
fun PreviewPostCard() {
    AppTheme {
        Surface {
            MoreView()
        }
    }
}