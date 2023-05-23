package com.example.githubsub.di

import com.example.githubsub.repository.issue.GithubIssueRepository
import com.example.githubsub.repository.issue.IGithubIssueRepository
import com.example.githubsub.repository.issuedetail.GithubIssueDetailRepository
import com.example.githubsub.repository.issuedetail.IGithubIssueDetailRepository
import com.example.githubsub.repository.repository.GithubProjectRepository
import com.example.githubsub.repository.repository.IGithubProjectRepository
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
    abstract fun bindIssueRepository(githubIssue: GithubIssueRepository): IGithubIssueRepository

    @Binds
    @Singleton
    abstract fun bindIssueDetailRepository(githubIssueDetail: GithubIssueDetailRepository): IGithubIssueDetailRepository

    @Binds
    @Singleton
    abstract fun bindGithubRepository(githubRepository: GithubProjectRepository): IGithubProjectRepository

    @Binds
    @Singleton
    abstract fun bindGithubUser(githubUser: GithubUser): IGithubUser
}