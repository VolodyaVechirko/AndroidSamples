package com.example.compose.old

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PressedSurface() {
    val (pressed, onPress) = remember { mutableStateOf(false) }
    val transition = updateTransition(
        targetState = if (pressed) SurfaceState.Pressed else SurfaceState.Released
    )

    val width by transition.animateDp { state ->
        when (state) {
            SurfaceState.Released -> 20.dp
            SurfaceState.Pressed -> 50.dp
        }
    }
    val surfaceColor by transition.animateColor { state ->
        when (state) {
            SurfaceState.Released -> Color.Blue
            SurfaceState.Pressed -> Color.Red
        }
    }
    val selectedAlpha by transition.animateFloat { state ->
        when (state) {
            SurfaceState.Released -> 0.5f
            SurfaceState.Pressed -> 1f
        }
    }

    Surface(
        color = surfaceColor.copy(alpha = selectedAlpha),
        modifier = Modifier
            .toggleable(value = pressed, onValueChange = onPress)
            .size(height = 200.dp, width = width)
    ) {

    }
}

enum class SurfaceState {
    Pressed,
    Released
}
