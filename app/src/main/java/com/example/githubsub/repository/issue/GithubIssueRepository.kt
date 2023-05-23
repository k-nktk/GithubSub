package com.example.githubsub.repository.issue


import com.example.githubsub.model.IssueList
import com.example.githubsub.repository.GithubInterface
import com.example.githubsub.repository.GithubRetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

//検索API
@Singleton
class GithubIssueRepository @Inject constructor(): IGithubIssueRepository {
    companion object {
        val retrofit = GithubRetrofitProvider().retrofit
    }

    override suspend fun searchIssue(userName: String, page: Int, clientID: String, clientSecret: String) : Response<IssueList> = withContext(Dispatchers.IO){
        val service = retrofit.create(GithubInterface::class.java)
        return@withContext service.searchIssues(userName, page, clientID, clientSecret).execute()
    }

}

