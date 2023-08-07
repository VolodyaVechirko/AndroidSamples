package com.vvechirko.nav_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.vvechirko.nav_compose.graphs.RootNavigationGraph
import com.vvechirko.nav_compose.ui.theme.TestProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestProjectTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}