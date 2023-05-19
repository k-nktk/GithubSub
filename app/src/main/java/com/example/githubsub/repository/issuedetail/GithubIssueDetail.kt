package com.example.githubsub.repository.issuedetail

import com.example.githubsub.model.SearchedIssue
import com.example.githubsub.model.SearchedIssueDetail
import com.example.githubsub.repository.GithubInterface
import com.example.githubsub.repository.GithubRetrofitProvider
import com.example.githubsub.repository.issue.IGithubIssue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubIssueDetail @Inject constructor(): IGithubIssueDetail {
    companion object {
        val retrofit = GithubRetrofitProvider().retrofit
    }

    @Inject
    override suspend fun searchIssueDetail(owner: String, repo: String, issueNumber: Int, clientID: String, clientSecret: String) : Response<List<SearchedIssueDetail>> = withContext(
        Dispatchers.IO){
        val service = retrofit.create(GithubInterface::class.java)
        retrofit.baseUrl()
        val commentsUrl = retrofit.baseUrl().toString() + "repos/${owner}/${repo}/issues/${issueNumber}/comments"
        return@withContext service.getIssueDetail(commentsUrl, clientID, clientSecret).execute()
    }

}