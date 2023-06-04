package com.example.compose.old

import androidx.compose.foundation.Image
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.testmaterial3.R

sealed class Tab(val route: String, val title: Int, val icon: Int) {
    object Android : Tab("Android", R.string.tab_android, R.drawable.ic_android)
    object Profile : Tab("Profile", R.string.tab_profile, R.drawable.ic_person)
    object Article : Tab("Article", R.string.tab_article, R.drawable.ic_article)
    object Gallery : Tab("Gallery", R.string.tab_gallery, R.drawable.ic_photo)
}

val navTabs = listOf(
    Tab.Android,
    Tab.Profile,
    Tab.Article,
    Tab.Gallery
)

@Composable
fun AppBottomNavigation(
    navController: NavHostController,
    items: List<Tab>
) {
    BottomNavigation {
        val currentRoute = navController.currentRoute()
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Image(painterResource(screen.icon), null) },
                label = { Text(stringResource(id = screen.title)) },
                selected = currentRoute == screen.route,
                alwaysShowLabel = false, // This hides the title for the unselected items
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController, startDestination = Tab.Android.route) {
        composable(Tab.Android.route) {
            Greeting(title = "Android", modifier = modifier, onClick = { })
        }
        composable(Tab.Profile.route) {
            val items = mutableListOf<String>().apply {
                repeat(20) { add("List item $it") }
            }
            NamePicker(header = "Header", names = items, modifier = modifier, onNameClicked = { })
        }
        composable(Tab.Article.route) {
            Surface {

            }
        }
        composable(Tab.Gallery.route) {
            Surface {

            }
        }
    }
}

@Composable
fun NavHostController.currentRoute(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
