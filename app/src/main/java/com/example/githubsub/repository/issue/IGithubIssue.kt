package com.example.githubsub.repository.issue

import com.example.githubsub.model.SearchedIssue
import com.example.githubsub.model.SearchedRepository
import retrofit2.Response

interface IGithubIssue {
    suspend fun searchIssue(query: String, page: Int) : Response<SearchedIssue>
}

//@Module
//@InstallIn(ViewModelComponent::class)
//    abstract class SearchModule {
//
//    @Binds
//    abstract fun bindGithubRepository(
//        githubRepository: GithubRepository
//    ): IGithubRepository
//}


//@Module
//@InstallIn(ViewModelComponent::class)
//abstract class SearchRepositoryModule {
//
//    @Binds
//    @ViewModelScoped
//    abstract fun searchRepository(impl: DefaultRepository): MainRepository
//}