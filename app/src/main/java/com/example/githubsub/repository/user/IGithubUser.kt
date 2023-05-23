package com.example.githubsub.repository.user

import com.example.githubsub.model.UserList
import retrofit2.Response

interface IGithubUser {
    suspend fun searchUser(userName: String, page: Int, clientID: String, clientSecret: String) : Response<UserList>

}