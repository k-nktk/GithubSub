package com.example.githubsub.ui.screen.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Person

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector?, val name: String) {
    object IssueList: Screen(route = "issueList", icon = Icons.Filled.Adjust, name = "Issue")
    object IssueDetail: Screen(route = "issueDetail", icon = null, name = "issueDetail")
    object UserList: Screen(route = "userList", icon = Icons.Filled.Person, name = "User")
    object RepositoryList: Screen(route = "repositoryList", icon = Icons.Filled.Book, name = "Repository")
}