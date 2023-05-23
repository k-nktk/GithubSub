package com.example.githubsub.repository.issue

import com.example.githubsub.model.IssueList
import retrofit2.Response

interface IGithubIssueRepository {
    suspend fun searchIssue(userName: String, page: Int, clientID: String, clientSecret: String) : Response<IssueList>
}