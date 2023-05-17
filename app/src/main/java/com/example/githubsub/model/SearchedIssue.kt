package com.example.githubsub.model

import com.squareup.moshi.Json

data class SearchedIssue(
    val items: List<SearchUserIssue>
)

data class SearchUserIssue(
    val id: Int,
    @Json(name = "repository_url") val repositoryUrl: String,
    @Json(name = "title") val title: String,
    @Json(name = "user") val user: User,
    @Json(name = "labels") val label: List<Label>
)

data class User(
    val id: Int,
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val imageUrl: String
)

data class Label(
    val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "color") val color: String
)