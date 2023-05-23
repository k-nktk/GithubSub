package com.example.githubsub.repository.issuedetail

import com.example.githubsub.model.IssueDetail
import retrofit2.Response

interface IGithubIssueDetailRepository {
    suspend fun searchIssueDetail(
        owner: String,
        projectName: String,
        issueNumber: Int,
        clientID: String,
        clientSecret: String
    ): Response<List<IssueDetail>>
}