package com.example.githubsub.ui.screen.navigation

sealed class Screen(val route: String) {
    object UserList: Screen(route = "userList")
    object RepositoryList: Screen(route = "repositoryList")
}