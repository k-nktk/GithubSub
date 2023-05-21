package com.example.githubsub.di

import com.example.githubsub.repository.datastore.DataStoreRepository
import com.example.githubsub.repository.datastore.IDataStoreRepository
import com.example.githubsub.repository.issue.GithubIssue
import com.example.githubsub.repository.issue.IGithubIssue
import com.example.githubsub.repository.issuedetail.GithubIssueDetail
import com.example.githubsub.repository.issuedetail.IGithubIssueDetail
import com.example.githubsub.repository.repository.GithubRepository
import com.example.githubsub.repository.repository.IGithubRepository
import com.example.githubsub.repository.user.GithubUser
import com.example.githubsub.repository.user.IGithubUser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindIssueRepository(githubIssue: GithubIssue): IGithubIssue

    @Binds
    @Singleton
    abstract fun bindIssueDetailRepository(githubIssueDetail: GithubIssueDetail): IGithubIssueDetail

    @Binds
    @Singleton
    abstract fun bindGithubRepository(githubRepository: GithubRepository): IGithubRepository

    @Binds
    @Singleton
    abstract fun bindGithubUser(githubUser: GithubUser): IGithubUser
}