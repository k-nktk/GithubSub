package com.example.githubsub.repository.issue


import com.example.githubsub.model.SearchedIssue
import com.example.githubsub.model.SearchedRepository
import com.example.githubsub.repository.GithubInterface
import com.example.githubsub.repository.GithubRetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

//検索API
@Singleton
class GithubIssue @Inject constructor(): IGithubIssue {
    companion object {
        val retrofit = GithubRetrofitProvider().retrofit
    }

    @Inject
    override suspend fun searchIssue(query: String, page: Int) : Response<SearchedIssue> = withContext(Dispatchers.IO){
        val service = retrofit.create(GithubInterface::class.java)
        return@withContext service.getSearchIssues(query, page).execute()
    }

}

