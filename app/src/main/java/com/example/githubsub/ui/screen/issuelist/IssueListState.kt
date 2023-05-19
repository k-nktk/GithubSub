package com.example.githubsub.ui.screen.issuelist
import com.example.githubsub.model.Label
import com.example.githubsub.model.SearchedIssue
import com.example.githubsub.model.SearchedRepository
import com.example.githubsub.model.SearchedUser


data class IssueListState(
    val issueListContent: List<IssueListItem>,
) {
    companion object {
        val initValue = IssueListState(
            issueListContent = mutableListOf()
        )
    }
}

data class IssueListItem(
    val issueTitle: String,
    val repositoryTitle: String,
    val user: String,
    val avatarUrl: String,
    val labels: List<Label>
)