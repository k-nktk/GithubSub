package com.example.githubsub.repository
import com.example.githubsub.model.*
import retrofit2.Call
import retrofit2.http.*

//APIの定義
interface GithubInterface {

    @GET("/search/repositories")
    fun searchProjects(@Query("q") projectName: String, @Query("per_page") page: Int, @Query("client_id") clientID: String, @Query("client_secret")clientSecret: String) : Call<ProjectList>

    @GET("/search/users")
    fun searchUsers(@Query("q") userName: String, @Query("per_page") page: Int, @Query("client_id") clientID: String, @Query("client_secret")clientSecret: String) : Call<UserList>

    @GET("/search/issues")
    fun searchIssues(@Query("q") userName: String, @Query("per_page") page: Int, @Query("client_id") clientID: String, @Query("client_secret")clientSecret: String) : Call<IssueList>

    @GET
    fun getIssuesProject(@Url url: String, @Query("client_id") clientID: String, @Query("client_secret")clientSecret: String): Call<ProjectItem>

    @Headers ("Accept: application/vnd.github+json")
    @GET
    fun getIssueDetail(@Url url: String, @Query("client_id") clientID: String, @Query("client_secret")clientSecret: String): Call<List<IssueDetail>>
}
