package com.example.githubsub.ui.screen.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Book

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector) {
    object UserList: Screen(route = "userList", icon = Icons.Filled.Adjust)
    object RepositoryList: Screen(route = "repositoryList", icon = Icons.Filled.Book)
}