package com.example.githubsub.ui.screen.userlist
import com.example.githubsub.model.UserList


data class UserListState(
    val userName: String,
    val userList: UserList,
    val mainUser: String,
) {
    companion object {
        val initValue = UserListState(
            userName = "",
            userList = UserList(mutableListOf()),
            mainUser = ""
        )
    }
}