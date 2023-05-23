package com.example.githubsub.repository.repository

import com.example.githubsub.model.ProjectItem
import com.example.githubsub.model.ProjectList
import retrofit2.Response

interface IGithubProjectRepository {
    suspend fun searchProject(userName: String, page: Int, clientID: String, clientSecret: String) : Response<ProjectList>

    suspend fun searchIssueProject(url: String, clientID: String, clientSecret: String): Response<ProjectItem>
}