package com.example.githubsub.ui.screen.IssueDetail

import com.example.githubsub.model.User

data class IssueDetailState(
    val issueTitle: String,
    val commentItems: List<CommentListItem>

) {
    companion object {
        val initValue = IssueDetailState(issueTitle = "", commentItems = mutableListOf())
    }
}

data class CommentListItem(
    val comment: String,
    val user: User
)