package com.vvechirko.camerax.more

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vvechirko.camerax.Route
import com.vvechirko.camerax.ui.AppTheme

@Composable
fun MoreScreen(navController: NavHostController = rememberNavController()) {
    Box(modifier = Modifier.fillMaxSize()) {
        val selected = remember { mutableStateOf(false) }
        val scale = animateFloatAsState(if (selected.value) 0.8f else 1f)

        Button(
            onClick = { navController.navigate(Route.SETTINGS) },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter)
                .scale(scale.value)
                .onButtonPressed { selected.value = it }
        ) {
            Text(text = "Material You palette")
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

@Preview(device = Devices.PIXEL_3)
@Composable
fun PreviewMoreScreen() {
    AppTheme {
        Surface {
            MoreScreen()
        }
    }
}