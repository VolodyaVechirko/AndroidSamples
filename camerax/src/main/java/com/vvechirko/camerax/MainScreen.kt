package com.vvechirko.camerax

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vvechirko.camerax.camera.CameraScreen
import com.vvechirko.camerax.friends.FriendsScreen
import com.vvechirko.camerax.home.HomeScreen
import com.vvechirko.camerax.home.PostFullScreen
import com.vvechirko.camerax.home.getPhoto
import com.vvechirko.camerax.more.EndlessScrollScreen
import com.vvechirko.camerax.more.MoreScreen
import com.vvechirko.camerax.more.SettingsScreen
import com.vvechirko.camerax.more.StickyHeaderScreen

sealed class Screen(
    val route: String,
    @StringRes val textRes: Int,
    @DrawableRes val iconRes: Int
) {
    data object Home : Screen(Route.HOME, R.string.title_home, R.drawable.ic_home)
    data object Camera : Screen(Route.CAMERA, R.string.title_camera, R.drawable.ic_camera)
    data object Friends : Screen(Route.FRIENDS, R.string.title_friends, R.drawable.ic_friends)
    data object More : Screen(Route.MORE, R.string.title_more, R.drawable.ic_person)
}


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
        topBar = { AppBar(title = "My Universe", navController) },
        bottomBar = { BottomNavigation(navController, bottomNavigationItems) },
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
        navController = navController,
        startDestination = Route.HOME,
        route = Route.ROOT,
        modifier = modifier
    ) {
        composable(Route.HOME) {
            HomeScreen(onPostClicked = { name ->
                navController.navigate("${Route.FULL_POST}/$name")
            })
        }
        composable(Route.CAMERA) {
            CameraScreen()
        }
        composable(Route.FRIENDS) {
            FriendsScreen()
        }
        composable(Route.MORE) {
            MoreScreen(navController)
        }

        composable(
            route = "${Route.FULL_POST}/{${Route.ARG_POST_ID}}",
            arguments = listOf(
                navArgument(Route.ARG_POST_ID) { type = NavType.StringType }
            )
        ) {
            val postId = it.arguments?.getString(Route.ARG_POST_ID)!!
            PostFullScreen(photo = getPhoto(LocalContext.current, postId))
        }

        composable(Route.SETTINGS) {
            SettingsScreen()
        }

        composable(Route.STICKY_HEADER) {
            StickyHeaderScreen()
        }

        composable(Route.ENDLESS_SCROLL) {
            EndlessScrollScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(title: String, navController: NavHostController) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = { navController.navigate(Route.STICKY_HEADER) }) {
                Icon(Icons.Filled.Share, null)
            }
            IconButton(onClick = { navController.navigate(Route.ENDLESS_SCROLL) }) {
                Icon(Icons.Filled.Settings, null)
            }
        }
    )
}

@Composable
private fun BottomNavigation(
    navController: NavHostController,
    items: List<Screen>
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.inverseOnSurface) {
        val currentRoute = navController.currentRoute()
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(screen.iconRes), contentDescription = null) },
                label = { Text(stringResource(screen.textRes)) },
                selected = (currentRoute == screen.route),
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
//                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
//                    indicatorColor = MaterialTheme.colorScheme.inversePrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
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

object Route {
    const val ROOT = "root"

    const val HOME = "nav_home"
    const val CAMERA = "nav_camera"
    const val FRIENDS = "nav_friends"
    const val MORE = "nav_more"

    const val FULL_POST = "full_post"
    const val SETTINGS = "settings"
    const val STICKY_HEADER = "sticky_headers"
    const val ENDLESS_SCROLL = "endless_scroll"

    const val ARG_POST_ID = "full_post_id"
}
