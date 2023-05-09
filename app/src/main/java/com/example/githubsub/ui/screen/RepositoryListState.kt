package com.example.githubsub.ui.screen

import com.example.githubsub.model.SearchItem
import com.example.githubsub.model.SearchResponse


data class RepositoryListState(
    val query: String,
    val searchResponse: SearchResponse
) {
    companion object {
        val initValue = RepositoryListState(
            query = "",
            searchResponse = SearchResponse(mutableListOf())
        )
    }
}