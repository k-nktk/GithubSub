package com.example.githubsub.ui.screen.issuelist
import com.example.githubsub.model.SearchedIssue
import com.example.githubsub.model.SearchedUser


data class IssueListState(
    val query: String,
    val searchedIssue: SearchedIssue
) {
    companion object {
        val initValue = IssueListState(
            query = "",
            searchedIssue = SearchedIssue(mutableListOf())
        )
    }
}