package com.example.compose.old

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Text("Hello World", modifier = Modifier.padding(20.dp).background(Color.Green))
//    ComposeTestTheme {
//        MainScreen()
//    }
}