package com.example.githubsub.ui.screen.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.githubsub.model.Label
import com.example.githubsub.ui.screen.IssueDetail.IssueDetail
import com.example.githubsub.ui.screen.issuelist.IssueList
import com.example.githubsub.ui.screen.issuelist.IssueListViewModel
import com.example.githubsub.ui.screen.repositorylist.RepositoryList
import com.example.githubsub.ui.screen.userlist.UserList

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.IssueList.route
) {
    val items = listOf(
        Screen.IssueList,
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
                        label = { Text(screen.name) },
                        icon = { screen.icon?.let { Icon(it, contentDescription = null) } },
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
        NavHost(navController, startDestination = startDestination, Modifier.padding(innerPadding)) {
            composable(Screen.IssueList.route) {
                IssueList (
                    onClickForNav = {
                            owner, repo, issueNumber, ownerImageUrl, issueTitle, issueLabel ->
                            navController.currentBackStackEntry?.run {
                                savedStateHandle["owner"] = owner
                                savedStateHandle["repo"] = repo
                                savedStateHandle["issueNumber"] = issueNumber
                                savedStateHandle["imageUrl"] = ownerImageUrl
                                savedStateHandle["issueTitle"] = issueTitle
                                savedStateHandle["issueLabel"] = issueLabel
                            }
                        Log.d("onclicktest-nav-detail", "${owner}/${repo}/${issueNumber}")

                        navController.navigate("issueDetail") {
                            popUpTo(Screen.IssueList.route)
                        }
                    }
                )
            }
            composable(Screen.UserList.route) { UserList() }
            composable(Screen.RepositoryList.route) { RepositoryList() }
            composable(Screen.IssueDetail.route) {
                val owner: String = navController.previousBackStackEntry?.savedStateHandle?.get<String>("owner")?:""
                val repo: String = navController.previousBackStackEntry?.savedStateHandle?.get<String>("repo")?:""
                val issueNumber: Int = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("issueNumber")?:0
                val ownerImageUrl: String = navController.previousBackStackEntry?.savedStateHandle?.get<String>("imageUrl")?:""
                val issueTitle: String = navController.previousBackStackEntry?.savedStateHandle?.get<String>("issueTitle")?:""
                val issueLabel: List<Label> = navController.previousBackStackEntry?.savedStateHandle?.get<List<Label>>("issueLabel")?: mutableListOf()

                Log.d("onclicktest-nav-detail", "${owner}/${repo}/${issueNumber}")

                IssueDetail(
                    onClickForNav = { navController.popBackStack()},
                    owner = owner,
                    repo = repo,
                    issueNumber = issueNumber,
                    ownerImageUrl = ownerImageUrl,
                    issueTitle = issueTitle,
                    issueLabel = issueLabel
                )
            }
        }
    }

}