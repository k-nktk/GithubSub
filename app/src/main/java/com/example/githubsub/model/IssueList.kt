package com.example.githubsub.model

import com.squareup.moshi.Json

data class IssueList(
    val items: List<IssueItem>
)

data class IssueItem(
    val id: Int,
    @Json(name = "repository_url") val repositoryUrl: String,
    @Json(name = "title") val title: String,
    @Json(name = "user") val user: UserItem,
    @Json(name = "labels") val label: List<Label>,
    @Json(name = "number") val number: Int
)

data class Label(
    val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "color") val color: String
)