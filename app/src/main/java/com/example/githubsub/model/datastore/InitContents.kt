package com.example.githubsub.model.datastore

data class InitContent (
    val listIssue: List<ListIssueItem>
)

data class ListIssueItem(
    val issueNumber: Int,
    val issueTitle: String,
    val projectTitle: String,
    val user: String,
    val imageUrl: String,
    val labelList: List<Label>
)

data class Label (
    val id: Int,
    val name: String,
    val color: String
)