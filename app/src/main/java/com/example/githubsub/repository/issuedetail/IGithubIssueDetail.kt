package com.example.githubsub.repository.issuedetail

import com.example.githubsub.model.SearchRepositoryItem
import com.example.githubsub.model.SearchedIssueDetail
import com.example.githubsub.model.SearchedRepository
import retrofit2.Response

interface IGithubIssueDetail {
    suspend fun searchIssueDetail(
        owner: String,
        repo: String,
        issueNumber: Int,
        clientID: String,
        clientSecret: String
    ): Response<List<SearchedIssueDetail>>
}