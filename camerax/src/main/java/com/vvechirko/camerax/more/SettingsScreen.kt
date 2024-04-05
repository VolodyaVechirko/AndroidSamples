package com.vvechirko.camerax.more

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vvechirko.camerax.ui.AppTheme

@Composable
fun SettingsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        val colors = colorList()
        LazyColumn {
            items(colors) {
                Surface(color = it.second, modifier = Modifier.fillMaxWidth()) {
                    Text(text = it.first, modifier = Modifier.padding(all = 8.dp))
                }
            }
        }
    }
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
fun PreviewSettingsScreen() {
    AppTheme {
        Surface {
            SettingsScreen()
        }
    }
}