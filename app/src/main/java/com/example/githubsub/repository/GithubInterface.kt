package com.example.githubsub.repository
import com.example.githubsub.model.SearchedRepository
import com.example.githubsub.model.SearchedUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//APIの定義
interface GithubInterface {

    @GET("/search/repositories")
    fun getSearchRepositories(@Query("q") query: String, @Query("per_page") page: Int) : Call<SearchedRepository>

    @GET("/search/users")
    fun getSearchUsers(@Query("q") query: String, @Query("per_page") page: Int) : Call<SearchedUser>
}