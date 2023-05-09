package com.example.githubsub.repository.repository

import com.example.githubsub.model.SearchedRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.Retrofit

interface IGithubRepository {
    suspend fun searchRepository(query: String, page: Int) : Response<SearchedRepository>
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