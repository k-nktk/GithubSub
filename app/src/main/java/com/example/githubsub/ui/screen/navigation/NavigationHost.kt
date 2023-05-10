package com.example.githubsub.ui.screen.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.githubsub.ui.screen.repositorylist.RepositoryList
import com.example.githubsub.ui.screen.userlist.UserList

//@Composable
//fun NavigationHost(
//    navController: NavHostController = rememberNavController(),
//    startDestination: String = Screen.UserList.route
//) {
//    NavHost(
//        navController = navController,
//        startDestination = startDestination
//    ) {
//        composable(route = Screen.UserList.route) {
//            UserList()
//        }
//    }
//}

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.UserList.route
) {
    val items = listOf(
        Screen.UserList,
        Screen.RepositoryList,
    )

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
//                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.UserList.route, Modifier.padding(innerPadding)) {
            composable(Screen.UserList.route) { UserList() }
            composable(Screen.RepositoryList.route) { RepositoryList() }
        }
    }

}