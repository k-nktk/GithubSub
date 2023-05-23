package com.example.githubsub.ui.screen.issuelist
import com.example.githubsub.model.Label


data class IssueListState(
    val issueList: List<IssueListItem>,
) {
    companion object {
        val initValue = IssueListState(
            issueList = mutableListOf()
        )
    }
}

data class IssueListItem(
    val issueNumber: Int,
    val issueTitle: String,
    val projectTitle: String,
    val user: String,
    val imageUrl: String,
    val labelList: List<Label>
)