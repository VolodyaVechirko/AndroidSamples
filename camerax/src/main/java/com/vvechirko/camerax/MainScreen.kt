package com.vvechirko.camerax

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vvechirko.camerax.camera.CameraView
import com.vvechirko.camerax.friends.FriendsView
import com.vvechirko.camerax.home.HomeView
import com.vvechirko.camerax.more.MoreView

sealed class Screen(
    val route: String,
    @StringRes val textRes: Int,
    @DrawableRes val iconRes: Int
) {
    object Home : Screen("Home", R.string.title_home, R.drawable.ic_home)
    object Camera : Screen("Camera", R.string.title_camera, R.drawable.ic_camera)
    object Friends : Screen("Friends", R.string.title_friends, R.drawable.ic_friends)
    object More : Screen("More", R.string.title_more, R.drawable.ic_person)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        Screen.Home,
        Screen.Camera,
        Screen.Friends,
        Screen.More
    )
    Scaffold(
        bottomBar = {
            BottomNavigation(navController, bottomNavigationItems)
        },
    ) { padding ->
        NavBarConfig(navController, modifier = Modifier.padding(padding))
    }
}


@Composable
private fun NavBarConfig(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController,
        startDestination = Screen.Friends.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeView()
        }
        composable(Screen.Camera.route) {
            CameraView()
        }
        composable(Screen.Friends.route) {
            FriendsView()
        }
        composable(Screen.More.route) {
            MoreView()
        }
    }
}

@Composable
private fun BottomNavigation(
    navController: NavHostController,
    items: List<Screen>
) {
    NavigationBar {
        val currentRoute = navController.currentRoute()
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(screen.iconRes), contentDescription = null) },
                label = { Text(stringResource(screen.textRes)) },
                selected = currentRoute == screen.route,
                alwaysShowLabel = false,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.popBackStack()
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}


@Composable
private fun NavHostController.currentRoute(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
