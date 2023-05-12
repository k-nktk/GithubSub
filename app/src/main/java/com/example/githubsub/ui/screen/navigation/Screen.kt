package com.example.githubsub.ui.screen.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Person

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector) {
    object IssueList: Screen(route = "issueList", icon = Icons.Filled.Adjust)
    object UserList: Screen(route = "userList", icon = Icons.Filled.Person)
    object RepositoryList: Screen(route = "repositoryList", icon = Icons.Filled.Book)
}