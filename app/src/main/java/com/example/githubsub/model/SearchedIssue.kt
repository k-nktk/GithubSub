package com.example.githubsub.model

import com.squareup.moshi.Json

data class SearchedIssue(
    val items: List<SearchUserIssue>
)

data class SearchUserIssue(
    val id: Int,
    @Json(name = "title") val title: String,
)