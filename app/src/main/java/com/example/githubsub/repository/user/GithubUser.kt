package com.example.githubsub.repository.user

import com.example.githubsub.model.UserList
import com.example.githubsub.repository.GithubInterface
import com.example.githubsub.repository.GithubRetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUser @Inject constructor(): IGithubUser {
    companion object {
        val retrofit = GithubRetrofitProvider().retrofit
    }

    override suspend fun searchUser(userName: String, page: Int, clientID: String, clientSecret: String) : Response<UserList> = withContext(Dispatchers.IO){
        val service = retrofit.create(GithubInterface::class.java)
        return@withContext service.searchUsers(userName, page, clientID, clientSecret).execute()
    }

}