package com.example.githubsub.repository.repository


import com.example.githubsub.model.ProjectItem
import com.example.githubsub.model.ProjectList
import com.example.githubsub.repository.GithubInterface
import com.example.githubsub.repository.GithubRetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

//検索API
@Singleton
class GithubProjectRepository @Inject constructor(): IGithubProjectRepository {
    companion object {
        val retrofit = GithubRetrofitProvider().retrofit
    }

    override suspend fun searchProject(userName: String, page: Int, clientID: String, clientSecret: String) : Response<ProjectList> = withContext(Dispatchers.IO){
        val service = retrofit.create(GithubInterface::class.java)
        return@withContext service.searchProjects(userName, page, clientID, clientSecret).execute()
    }

    override suspend fun searchIssueProject(url: String, clientID: String, clientSecret: String): Response<ProjectItem>  = withContext(Dispatchers.IO){
        val service = retrofit.create(GithubInterface::class.java)
        return@withContext service.getIssuesProject(url, clientID, clientSecret).execute()
    }

}

