package com.example.githubsub.repository


import com.example.githubsub.model.SearchResponse
import retrofit2.Response
import retrofit2.Retrofit

//検索API
class GithubRepository(private val retrofit: Retrofit) {

    suspend fun searchRepository(query: String) : Response<SearchResponse> {
        val service = retrofit.create(GithubInterface::class.java)
        return service.getSearchRepositories(query).execute()
    }

}