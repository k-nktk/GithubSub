package com.example.githubsub.repository
import com.example.githubsub.model.*
import retrofit2.Call
import retrofit2.http.*

//APIの定義
interface GithubInterface {

    @GET("/search/repositories")
    fun getSearchRepositories(@Query("q") query: String, @Query("per_page") page: Int, @Query("client_id") clientID: String, @Query("client_secret")clientSecret: String) : Call<SearchedRepository>

    @GET("/search/users")
    fun getSearchUsers(@Query("q") query: String, @Query("per_page") page: Int, @Query("client_id") clientID: String, @Query("client_secret")clientSecret: String) : Call<SearchedUser>

    @GET("/search/issues")
    fun getSearchIssues(@Query("q") query: String, @Query("per_page") page: Int, @Query("client_id") clientID: String, @Query("client_secret")clientSecret: String) : Call<SearchedIssue>

    @GET
    fun getIssueRepository(@Url url: String, @Query("client_id") clientID: String, @Query("client_secret")clientSecret: String): Call<SearchRepositoryItem>

    @Headers ("Accept: application/vnd.github+json")
    @GET
    fun getIssueDetail(@Url url: String, @Query("client_id") clientID: String, @Query("client_secret")clientSecret: String): Call<List<SearchedIssueDetail>>
}
