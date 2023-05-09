package com.example.githubsub.repository.repository


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
class GithubRepository @Inject constructor(): IGithubRepository {
    companion object {
        val retrofit = GithubRetrofitProvider().retrofit
    }

    @Inject
    override suspend fun searchRepository(query: String, page: Int) : Response<SearchedRepository> = withContext(Dispatchers.IO){
        val service = retrofit.create(GithubInterface::class.java)
        return@withContext service.getSearchRepositories(query, page).execute()
    }

}

