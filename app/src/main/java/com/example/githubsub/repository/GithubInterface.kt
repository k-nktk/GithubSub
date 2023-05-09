package com.example.githubsub.repository
import com.example.githubsub.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//APIの定義
interface GithubInterface {

    @GET("/search/repositories")
    fun getSearchRepositories(@Query("q") query: String) : Call<SearchResponse>

}