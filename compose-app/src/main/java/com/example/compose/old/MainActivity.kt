package com.example.compose.old

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.compose.old.theme.ComposeTestTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            ProvideWindowInsets {
            ComposeTestTheme(darkTheme = false) {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val tabs = navTabs
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { AppBottomNavigation(navController, tabs) }
    ) { innerPadding ->
        MainNavHost(
            navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}