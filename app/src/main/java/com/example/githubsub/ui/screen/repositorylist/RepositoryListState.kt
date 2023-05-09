package com.example.githubsub.ui.screen.repositorylist
import com.example.githubsub.model.SearchedRepository


data class RepositoryListState(
    val query: String,
    val searchResponse: SearchedRepository
) {
    companion object {
        val initValue = RepositoryListState(
            query = "",
            searchResponse = SearchedRepository(mutableListOf())
        )
    }
}