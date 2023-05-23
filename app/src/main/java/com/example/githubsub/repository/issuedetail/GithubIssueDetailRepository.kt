package com.example.githubsub.repository.issuedetail

import com.example.githubsub.model.IssueDetail
import com.example.githubsub.repository.GithubInterface
import com.example.githubsub.repository.GithubRetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubIssueDetailRepository @Inject constructor(): IGithubIssueDetailRepository {
    companion object {
        val retrofit = GithubRetrofitProvider().retrofit
    }

    override suspend fun searchIssueDetail(owner: String, projectName: String, issueNumber: Int, clientID: String, clientSecret: String) : Response<List<IssueDetail>> = withContext(
        Dispatchers.IO){
        val service = retrofit.create(GithubInterface::class.java)
        retrofit.baseUrl()
        val commentsUrl = retrofit.baseUrl().toString() + "repos/${owner}/${projectName}/issues/${issueNumber}/comments"
        return@withContext service.getIssueDetail(commentsUrl, clientID, clientSecret).execute()
    }

}