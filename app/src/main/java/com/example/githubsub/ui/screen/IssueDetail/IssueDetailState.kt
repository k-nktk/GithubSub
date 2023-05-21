package com.example.githubsub.ui.screen.IssueDetail

import com.example.githubsub.model.User

data class IssueDetailState(
    val commentItems: List<CommentListItem>

) {
    companion object {
        val initValue = IssueDetailState(commentItems = mutableListOf())
    }
}

data class CommentListItem(
    val comment: String,
    val user: User
)