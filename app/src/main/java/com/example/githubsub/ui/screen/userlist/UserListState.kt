package com.example.githubsub.ui.screen.userlist
import com.example.githubsub.model.SearchedUser


data class UserListState(
    val query: String,
    val searchedUser: SearchedUser,
    val mainUser: String,
) {
    companion object {
        val initValue = UserListState(
            query = "",
            searchedUser = SearchedUser(mutableListOf()),
            mainUser = ""
        )
    }
}