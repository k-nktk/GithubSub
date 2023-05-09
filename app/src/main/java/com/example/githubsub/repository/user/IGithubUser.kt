package com.example.githubsub.repository.user

import com.example.githubsub.model.SearchedUser
import retrofit2.Response

interface IGithubUser {
    suspend fun searchUser(query: String, page: Int) : Response<SearchedUser>
}