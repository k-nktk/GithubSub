package com.example.githubsub.repository


import com.example.githubsub.model.SearchResponse
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

//検索API
@Singleton
class GithubRepository @Inject constructor(): IGithubRepository {
    companion object {
        val retrofit = GithubRetrofitProvider().retrofit
    }

    @Inject
    override suspend fun searchRepository(query: String, page: Int) : Response<SearchResponse> = withContext(Dispatchers.IO){
        val service = retrofit.create(GithubInterface::class.java)
        return@withContext service.getSearchRepositories(query, page).execute()
    }

}